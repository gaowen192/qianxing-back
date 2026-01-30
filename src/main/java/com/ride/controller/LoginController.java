package com.ride.controller;

import com.ride.common.Result;
import com.ride.dto.GoogleLoginRequest;
import com.ride.dto.LoginRequest;
import com.ride.dto.LoginResponse;
import com.ride.dto.TcmUserDTO;
import com.ride.entity.TcmUser;
import com.ride.service.LoginService;
import com.ride.service.TcmUserService;
import com.ride.util.GoogleTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * 登录控制器
 * 处理用户登录相关的HTTP请求
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户登录、注销等认证相关接口")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private TcmUserService tcmUserService;
    
    @Autowired
    private GoogleTokenUtil googleTokenUtil;

    /**
     * 用户登录接口
     * 接收用户名和密码，返回登录结果和访问令牌
     *
     * @param loginRequest 登录请求参数
     * @return 登录响应结果
     */
    @Operation(summary = "用户登录", description = "用户通过用户名和密码进行登录认证")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "登录成功", 
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "参数错误或用户名密码错误"),
        @ApiResponse(responseCode = "403", description = "账户被禁用"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("=============== Received login request from user: {}", loginRequest.getUsername());
        
        try {
            // 调用登录服务进行认证
            LoginResponse response = loginService.login(loginRequest);
            log.info("=============== Login successful for user: {}", loginRequest.getUsername());
            return Result.success("登录成功", response);
        } catch (IllegalArgumentException e) {
            log.warn("=============== Login failed: {}", e.getMessage());
            
            if (e.getMessage().contains("禁用")) {
                return Result.forbidden(e.getMessage());
            }
            return Result.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("=============== Login error", e);
            return Result.error("服务器内部错误，请稍后重试");
        }
    }

    /**
     * 用户注销接口
     * 使当前用户的访问令牌失效
     *
     * @param userId 用户ID
     * @return 注销结果
     */
    @Operation(summary = "用户注销", description = "用户退出登录，使当前令牌失效")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "注销成功"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/logout")
    public Result<?> logout(@RequestParam String userId) {
        log.info("=============== Received logout request for user: {}", userId);
        
        try {
            loginService.logout(userId);
            log.info("=============== Logout successful for user: {}", userId);
            return Result.success("退出成功");
        } catch (Exception e) {
            log.error("=============== Logout error", e);
            return Result.error("退出失败，请稍后重试");
        }
    }

    /**
     * 刷新访问令牌接口
     * 使用旧令牌获取新的访问令牌
     *
     * @param oldToken 旧的访问令牌
     * @return 新的访问令牌
     */
    @Operation(summary = "刷新令牌", description = "使用有效的旧令牌获取新的访问令牌")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "令牌刷新成功"),
        @ApiResponse(responseCode = "401", description = "旧令牌无效或已过期"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/refresh-token")
    public Result<String> refreshToken(@RequestParam String oldToken) {
        log.info("=============== Received refresh token request");
        
        try {
            String newToken = loginService.refreshToken(oldToken);
            log.info("=============== Token refreshed successfully");
            return Result.success("令牌刷新成功", newToken);
        } catch (Exception e) {
            log.error("=============== Token refresh error", e);
            return Result.unauthorized("令牌无效或已过期");
        }
    }
    
    /**
     * Google用户登录接口
     * 接收Google用户信息和token，验证token有效性，保存或更新用户信息
     * 
     * @param googleLoginRequest Google登录请求参数
     * @return 登录响应结果
     */
    @Operation(summary = "Google用户登录", description = "Google用户通过Google token进行登录认证")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "登录成功", 
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "参数错误或Google token无效"),
        @ApiResponse(responseCode = "403", description = "账户被禁用"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/google-login")
    public Result<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest googleLoginRequest) {
        log.info("=============== Received Google login request for user: {}", googleLoginRequest.getEmail());
        
        try {
            // 验证Google Token的有效性
            Map<String, Object> tokenVerificationResult = googleTokenUtil.verifyGoogleToken(googleLoginRequest.getGoogleAccessToken());
            boolean isValid = (Boolean) tokenVerificationResult.get("valid");
            
            if (!isValid) {
                log.warn("=============== Google token verification failed for user: {}", googleLoginRequest.getEmail());
                return Result.badRequest("Google token无效");
            }
            
            // 从验证结果中提取用户信息
            Map<String, String> googleUserInfo = (Map<String, String>) tokenVerificationResult.get("userInfo");
            
            // 创建或更新用户信息
            TcmUser googleUser = new TcmUser();
            googleUser.setGoogleId(googleUserInfo.get("googleId"));
            googleUser.setEmail(googleUserInfo.get("email"));
            googleUser.setUsername(googleUserInfo.get("email").split("@")[0]); // 使用邮箱前缀作为用户名
            googleUser.setRealName(googleUserInfo.get("name"));
            googleUser.setAvatar(googleUserInfo.get("avatar"));
            googleUser.setGoogleAccessToken(googleLoginRequest.getGoogleAccessToken());
            googleUser.setGoogleRefreshToken(googleLoginRequest.getGoogleRefreshToken());
            googleUser.setGoogleTokenExpiry(googleLoginRequest.getGoogleTokenExpiry());
            googleUser.setLastLoginTime(LocalDateTime.now());
            
            // 保存或更新Google用户信息
            TcmUserDTO userDTO = tcmUserService.saveOrUpdateGoogleUser(googleUser);
            
            // 生成JWT令牌
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(userDTO.getId());
            loginResponse.setUsername(userDTO.getUsername());
            loginResponse.setEmail(userDTO.getEmail());
            loginResponse.setRealName(userDTO.getRealName());
            loginResponse.setAvatar(userDTO.getAvatar());
            loginResponse.setStatus(userDTO.getStatus());
            loginResponse.setUserType(userDTO.getUserType());
            loginResponse.setHasShop(userDTO.getHasShop());
            loginResponse.setAccessToken(loginService.generateToken(userDTO));
            loginResponse.setTokenExpireTime(LocalDateTime.now().plusHours(24)); // 24小时过期
            
            log.info("=============== Google login successful for user: {}", googleUserInfo.get("email"));
            return Result.success("Google登录成功", loginResponse);
        } catch (IllegalArgumentException e) {
            log.warn("=============== Google login failed: {}", e.getMessage());
            
            if (e.getMessage().contains("禁用")) {
                return Result.forbidden(e.getMessage());
            }
            return Result.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("=============== Google login error", e);
            return Result.error("服务器内部错误，请稍后重试");
        }
    }
}