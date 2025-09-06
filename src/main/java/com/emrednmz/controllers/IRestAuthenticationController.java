package com.emrednmz.controllers;

import com.emrednmz.dto.request.AuthRequest;
import com.emrednmz.dto.request.RefreshTokenRequest;
import com.emrednmz.dto.request.RegisterRequest;
import com.emrednmz.dto.response.AuthResponse;
import com.emrednmz.dto.response.DtoUser;

public interface IRestAuthenticationController {
    public RootEntity<DtoUser> register(RegisterRequest registerRequest);
    public RootEntity<AuthResponse> authenticate(AuthRequest authRequest);
    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
