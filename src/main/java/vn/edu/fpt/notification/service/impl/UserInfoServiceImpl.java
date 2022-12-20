package vn.edu.fpt.notification.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.dto.cache.UserInfo;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.service.UserInfoService;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 23/11/2022 - 08:33
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public UserInfo getUserInfo(String accountId) {
        try {
            String userInfoStr = redisTemplate.opsForValue().get(String.format("userinfo:%s", accountId));
            return objectMapper.readValue(userInfoStr, UserInfo.class);
        }catch (Exception ex){
            log.info("Can't get userinfo in redis: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public String getAccountId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getUsername).orElseThrow(() -> new BusinessException("Can't get account id by token"));
    }
}
