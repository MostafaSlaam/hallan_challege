package com.example.halanchallenge.di



import com.example.halanchallenge.BuildConfig
import com.example.halanchallenge.repository.ApiClient
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun provideOkHttpClientBuilder(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.sslSocketFactory(
            provideSSLSocketFactory()!!,
            (provideTrustManager()[0] as X509TrustManager?)!!
        )
        builder.hostnameVerifier(HostnameVerifier { hostName: String?, session: SSLSession? -> true })
        builder.connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
        builder.addInterceptor { chain ->
//            val token = sharedPref.getString(USER_TOKEN, null)
//            val currentLanguage = sharedPref.getString(LANG_KEY, Locale.getDefault().language)
            val newRequest = chain.request().newBuilder()
                .addHeader("Connection", "close")
                .build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(provideHttpLoggingInterceptor())
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClientBuilder())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun providesApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideSSLSocketFactory(): SSLSocketFactory? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, provideTrustManager(), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sslContext!!.socketFactory
    }

    @Provides
    fun provideTrustManager(): Array<TrustManager?> {
        return arrayOf(
            object : X509TrustManager {
                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }


}