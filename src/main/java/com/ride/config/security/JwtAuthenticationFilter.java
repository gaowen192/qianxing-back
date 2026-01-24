package com.ride.config.security;

import com.ride.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ride.common.Result;
import com.ride.dto.TcmUserDTO;
import com.ride.entity.TcmUser;
import com.ride.service.TcmUserService;
import com.ride.util.GoogleTokenUtil;

/**
 * JWT认证过滤器
 * 拦截请求并验证JWT令牌
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private GoogleTokenUtil googleTokenUtil;

    @Autowired
    private TcmUserService tcmUserService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("=============== JwtAuthenticationFilter processing request: " + requestURI);
        
        boolean isAuthenticated = false;
        
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            logger.info("========= Authorization header ====== {}", authHeader);
            
            // 从请求头中获取token
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                logger.warn("=============== Token does not begin with Bearer String");
            }

            // 如果找到了token且当前安全上下文中没有认证信息
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 1. 首先尝试JWT token验证
                try {
                    logger.info("=============== Trying JWT token validation");
                    String username = jwtTokenUtil.getUsernameFromToken(token);
                    
                    if (username != null && jwtTokenUtil.validateToken(token)) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        // 设置认证信息到安全上下文
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        logger.info("=============== User {} authenticated successfully with JWT token", username);
                        isAuthenticated = true;
                    }
                } catch (Exception e) {
                    // JWT验证失败，尝试Google token验证
                    logger.info("=============== JWT token validation failed, trying Google token validation: {}", e.getMessage());
                    
                    try {
                        // 2. 尝试Google token验证
                        Map<String, Object> tokenVerificationResult = googleTokenUtil.verifyGoogleToken(token);
                        boolean isValid = (Boolean) tokenVerificationResult.get("valid");
                        
                        if (isValid) {
                            // 从验证结果中提取用户信息
                            Map<String, String> googleUserInfo = (Map<String, String>) tokenVerificationResult.get("userInfo");
                            String googleId = googleUserInfo.get("googleId");
                            
                            // 根据Google ID查找用户
                            TcmUserDTO userDTO = tcmUserService.getUserByGoogleId(googleId);
                            
                            // 加载UserDetails
                            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDTO.getUsername());
                            
                            // 设置认证信息
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            logger.info("=============== User {} authenticated successfully with Google token", userDTO.getUsername());
                            isAuthenticated = true;
                        }
                    } catch (UsernameNotFoundException ex) {
                        logger.error("=============== User not found during Google token authentication: {}", ex.getMessage());
                    } catch (Exception ex) {
                        logger.error("=============== Google token validation failed: {}", ex.getMessage());
                    }
                }
            }
            
            // 如果没有认证成功，继续过滤链，让后续的安全机制处理
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.error("=============== JWT token expired: {}", e.getMessage());
            handleJwtException(response, Result.unauthorized("JWT token expired"));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            logger.error("=============== Invalid JWT token: {}", e.getMessage());
            handleJwtException(response, Result.unauthorized("Invalid JWT token"));
        } catch (IllegalArgumentException e) {
            logger.error("=============== JWT claims string is empty: {}", e.getMessage());
            handleJwtException(response, Result.unauthorized("JWT token is empty"));
        }
    }

    /**
     * 处理JWT异常，返回标准化的错误响应
     */
    private void handleJwtException(HttpServletResponse response, Result<?> result) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
