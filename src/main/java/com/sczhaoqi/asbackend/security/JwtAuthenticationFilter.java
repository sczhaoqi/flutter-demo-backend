package com.sczhaoqi.asbackend.security;

import com.sczhaoqi.asbackend.domain.User;
import com.sczhaoqi.asbackend.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author sczhaoqi
 * @date 2019/6/27 23:18
 */
@Component
@Slf4j
public class JwtAuthenticationFilter
        extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        try {
            String authToken = request.getHeader(jwtTokenUtil.getTokenHeader());
            if (authToken != null) {
                User user = jwtTokenUtil.getUser(authToken);
                if(user == null){
                    throw new AuthenticationCredentialsNotFoundException("Invalid token");
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorityInfo());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                log.info("authenticated user " + user.getUsername() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception ex) {
            log.error("token 解析失败", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        chain.doFilter(request, response);
    }
}
