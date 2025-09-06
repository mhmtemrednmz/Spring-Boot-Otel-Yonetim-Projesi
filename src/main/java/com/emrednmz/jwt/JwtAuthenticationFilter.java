package com.emrednmz.jwt;

import com.emrednmz.exception.MessageType;
import com.emrednmz.exception.handler.ApiError;
import com.emrednmz.exception.handler.Exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.replace("Bearer ", "");
            String username = jwtService.getUsernameByToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            sendErrorResponse(response, MessageType.TOKEN_IS_EXPIRED, "JWT token süresi dolmus", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (SignatureException ex) {
            sendErrorResponse(response, MessageType.INVALID_TOKEN, "JWT imzası gecersiz", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (JwtException ex) {
            sendErrorResponse(response, MessageType.INVALID_TOKEN, "Gecersiz JWT: " + ex.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, MessageType type, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        ApiError<Object> apiError = new ApiError<>();
        Exception<Object> exception = new Exception<>();
        exception.setMessage(message);
        exception.setPath("JWT Filter");
        exception.setTimestamp(new Date());
        exception.setHostname(getHostName());

        apiError.setMessage(exception);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }

    private String getHostName(){
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        return "";
    }
}
