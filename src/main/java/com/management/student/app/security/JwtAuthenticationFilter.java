package com.management.student.app.security;

import com.management.student.app.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String header;

    @Value("${jwt.token.prefix}")
    public String tokenPrefix;

    @Resource(name = "userService")
    private UserService userDetailsService;

    private final TokenProvider jwtTokenUtil;
    private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(TokenProvider jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        String requestHeader = servletRequest.getHeader (this.header);
        String email = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith (tokenPrefix)) {
            authToken = requestHeader.replace (tokenPrefix, "");
            try {
                email = jwtTokenUtil.getEmailFromToken (authToken);
            } catch (IllegalArgumentException e) {
                LOGGER.error ("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                LOGGER.warn ("The token has expired", e);
            } catch (SignatureException e) {
                LOGGER.error ("Authentication Failed. Username or Password not valid.");
            }
        } else {
            LOGGER.warn ("Couldn't find bearer string, header will be ignored");
        }
        if (email != null && SecurityContextHolder.getContext ().getAuthentication () == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername (email);

            if (jwtTokenUtil.validateToken (authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken (authToken, userDetails);
                authentication.setDetails (new WebAuthenticationDetailsSource ().buildDetails (servletRequest));
                SecurityContextHolder.getContext ().setAuthentication (authentication);
            }
        }

        chain.doFilter (servletRequest, servletResponse);
    }
}