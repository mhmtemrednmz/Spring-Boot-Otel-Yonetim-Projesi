package com.emrednmz.services.impl;

import com.emrednmz.dto.request.AuthRequest;
import com.emrednmz.dto.request.RefreshTokenRequest;
import com.emrednmz.dto.request.RegisterRequest;
import com.emrednmz.dto.response.AuthResponse;
import com.emrednmz.dto.response.DtoUser;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.jwt.JwtService;
import com.emrednmz.mapper.UserMapper;
import com.emrednmz.model.RefreshToken;
import com.emrednmz.model.Role;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.RefreshTokenRepository;
import com.emrednmz.repositories.RoleRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.security.UserDetailsImpl;
import com.emrednmz.services.IAutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAutenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Override
    public DtoUser register(RegisterRequest registerRequest) {
        if (emailIsUnique(registerRequest)) {
            UserEntity entity = userMapper.toEntity(registerRequest);
            List<Role> roleEntities = roleRepository.findByNameIn(registerRequest.getRoles());
            entity.setRoles(roleEntities);

            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
            entity.setCreateTime(new Date());
            UserEntity savedUser = userRepository.save(entity);
            return userMapper.toDto(savedUser);
        }
        else {
            throw new BaseException(new ErrorMessage(MessageType.EMAIL_ALREADY_EXISTS,registerRequest.getEmail()));
        }


    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<UserEntity> userEntity = userRepository.findByEmail(authRequest.getEmail());
            UserDetailsImpl userDetails = UserDetailsImpl.build(userEntity.get());

            String access_token = jwtService.generateToken(userDetails);
            RefreshToken refreshToken = createRefreshToken(userEntity.get());
            refreshTokenRepository.save(refreshToken);


            return new AuthResponse(access_token, refreshToken.getRefreshToken());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.EMAIL_OR_PASSWORD_MISMATCH, authRequest.getEmail()));
        }

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if (optRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_FOUND_REFRESH_TOKEN, refreshTokenRequest.getRefreshToken()));
        }
        UserDetailsImpl userDetails = UserDetailsImpl.build(optRefreshToken.get().getUser());
        String access_token = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = createRefreshToken(optRefreshToken.get().getUser());
        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(access_token, refreshToken.getRefreshToken());
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("ROLE_" + role));
    }

    private boolean emailIsUnique(RegisterRequest registerRequest) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(registerRequest.getEmail());
        return userEntity.isEmpty();
    }

    private RefreshToken createRefreshToken(UserEntity userEntity) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        return refreshToken;
    }


}
