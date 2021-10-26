package com.example.halanchallenge.model;

import com.example.halanchallenge.ui.profile.Profile;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public String token;
    public Profile profile;
}
