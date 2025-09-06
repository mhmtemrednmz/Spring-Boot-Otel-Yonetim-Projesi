package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestAuthenticationController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.AuthRequest;
import com.emrednmz.dto.request.RefreshTokenRequest;
import com.emrednmz.dto.request.RegisterRequest;
import com.emrednmz.dto.response.AuthResponse;
import com.emrednmz.dto.response.DtoUser;
import com.emrednmz.services.IAutenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {
    @Autowired
    private IAutenticationService autenticationService;

    @Override
    @PostMapping("register")
    public RootEntity<DtoUser> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ok(autenticationService.register(registerRequest));
    }

    @Override
    @PostMapping("authenticate")
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return ok(autenticationService.authenticate(authRequest));
    }

    @Override
    @PostMapping("refreshToken")
    public RootEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return ok(autenticationService.refreshToken(refreshTokenRequest));
    }
}
