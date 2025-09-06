package com.emrednmz.services;

import com.emrednmz.dto.request.AuthRequest;
import com.emrednmz.dto.request.RefreshTokenRequest;
import com.emrednmz.dto.request.RegisterRequest;
import com.emrednmz.dto.response.AuthResponse;
import com.emrednmz.dto.response.DtoUser;
import com.emrednmz.model.UserEntity;

public interface IAutenticationService {
    public DtoUser register(RegisterRequest registerRequest);
    public AuthResponse authenticate(AuthRequest authRequest);
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    public UserEntity getCurrentUser();
    public boolean hasRole(String role);
}
