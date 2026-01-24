package com.ride.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Google Token验证工具类
 * 提供Google ID Token的验证和解析功能
 */
@Component
public class GoogleTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenUtil.class);
    private static final String GOOGLE_TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=%s";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 验证Google ID Token的有效性
     * 
     * @param idToken Google ID Token
     * @return 验证结果和解析后的用户信息
     */
    public Map<String, Object> verifyGoogleToken(String idToken) {
        Map<String, Object> result = new HashMap<>();
        boolean isValid = false;
        Map<String, String> userInfo = new HashMap<>();

        try {
            // 构建请求URL
            String urlString = String.format(GOOGLE_TOKEN_INFO_URL, idToken);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 获取响应
            int responseCode = connection.getResponseCode();
            logger.info("Google Token验证请求响应码: {}", responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应内容
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析JSON响应
                JsonNode jsonNode = objectMapper.readTree(response.toString());
                logger.debug("Google Token验证响应: {}", jsonNode);

                // 验证成功
                isValid = true;
                
                // 提取用户信息
                if (jsonNode.has("sub")) {
                    userInfo.put("googleId", jsonNode.get("sub").asText());
                }
                if (jsonNode.has("email")) {
                    userInfo.put("email", jsonNode.get("email").asText());
                }
                if (jsonNode.has("name")) {
                    userInfo.put("name", jsonNode.get("name").asText());
                }
                if (jsonNode.has("picture")) {
                    userInfo.put("avatar", jsonNode.get("picture").asText());
                }
                if (jsonNode.has("given_name")) {
                    userInfo.put("givenName", jsonNode.get("given_name").asText());
                }
                if (jsonNode.has("family_name")) {
                    userInfo.put("familyName", jsonNode.get("family_name").asText());
                }
            } else {
                // 验证失败
                logger.error("Google Token验证失败，响应码: {}", responseCode);
            }
        } catch (Exception e) {
            logger.error("Google Token验证过程中发生异常: {}", e.getMessage(), e);
        }

        result.put("valid", isValid);
        result.put("userInfo", userInfo);
        return result;
    }
}
