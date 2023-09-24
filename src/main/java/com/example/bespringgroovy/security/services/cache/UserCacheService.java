package com.example.bespringgroovy.security.services.cache;

import com.example.bespringgroovy.converter.UserConverter;
import com.example.bespringgroovy.dto.UserDTO;
import com.example.bespringgroovy.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * UserCacheService Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
@Slf4j
public class UserCacheService {
  private final String START_KEY = "user";
  private final String SEPARATOR = "_";

  @Autowired
  private UserConverter userConverter;

  @Autowired
  private RedisTemplate<String, UserDTO> redisTemplate;

  public void saveUser(User user) {
    String key = START_KEY + SEPARATOR+ user.getEmail();
    redisTemplate.opsForValue().set(key, userConverter.convertToDto(user), 10, TimeUnit.HOURS);
    log.info("SaveUser() : cache updated >> " + user);
  }

  public User getUser(String email) {
    return userConverter.convertToEntity(redisTemplate.opsForValue().get(START_KEY + SEPARATOR + email));
  }

  public void updateUser(User user) {
    redisTemplate.delete(START_KEY + user.getEmail());
    saveUser(user);
    log.info("UpdateUser() : cache updated >> " + user);
  }
  public boolean exists(String email) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(START_KEY + SEPARATOR + email));
  }

}
