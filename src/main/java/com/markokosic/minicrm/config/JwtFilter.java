package com.markokosic.minicrm.config;


import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.context.TenantContextHolder;
import com.markokosic.minicrm.exception.AuthException;
import com.markokosic.minicrm.service.JWTService;
import com.markokosic.minicrm.service.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Long tenantId = null;

        // Extract JWT from cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        try {
//            if (token != null) {
//                username = jwtService.extractEmail(token);
//                tenantId = jwtService.extractTenantId(token);
//            }
//
//            try {
//
//                if (username != null && tenantId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
//                    if (jwtService.validateToken(token, userDetails)) {
//                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                        TenantContextHolder.setTenantId(tenantId);
//                    }
//                }
//            } catch (ExpiredJwtException e){
//                throw new ExpiredAuthTokenException();
//            }
//            filterChain.doFilter(request, response);


            if (token != null) {
                try {
                    username = jwtService.extractEmail(token);
                    tenantId = jwtService.extractTenantId(token);

                    if (username != null && tenantId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                        if (jwtService.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            TenantContextHolder.setTenantId(tenantId);
                        }
                    }
                }
                catch (ExpiredJwtException e) {
                    throw new AuthException(ApiErrorCode.AUTH_TOKEN_EXPIRED);
                }
            }
            filterChain.doFilter(request, response);

        } finally {
            TenantContextHolder.clear();
        }
    }
}
