package com.example.bespringgroovy.service.impl;

import com.example.bespringgroovy.constant.RoleConstants;
import com.example.bespringgroovy.entity.AuthProvider;
import com.example.bespringgroovy.entity.Role;
import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.exception.ResourceNotFoundException;
import com.example.bespringgroovy.repo.RoleRepo;
import com.example.bespringgroovy.repo.UserRepo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfo;
import com.example.bespringgroovy.security.services.UserDetailsCustom;
import com.example.bespringgroovy.security.services.cache.UserCacheService;
import com.example.bespringgroovy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * UserServiceImpl Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private RoleRepo roleRepo;
  @Lazy
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserCacheService userCacheService;
  @Override
  public Optional<User> getByUsernameAndStatus(String username, User.UserStatus status) {
    return userRepo.findByUsernameAndStatus(username, status);
  }

  @Override
  public User updateUserOauth2(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setName(oAuth2UserInfo.getName());
//    existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    long start = System.nanoTime();
    User userSaved = userRepo.save(existingUser);
    long end = System.nanoTime();
    long timeTaken = end - start;
    log.debug("Time updated user: {} milliseconds", timeTaken/ 1_000_000);
    log.debug("Updated user: {}", userSaved);
    if (userCacheService.exists(existingUser.getEmail())) {
      userCacheService.updateUser(existingUser);
    }
    return userSaved;
  }

  @Override
  public User addUserOauth2(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    Role role = roleRepo.findByName(RoleConstants.USER)
      .orElseThrow(() -> new ResourceNotFoundException("Role", "name", RoleConstants.USER));
    User user = User.builder()
      .username(oAuth2UserInfo.getEmail())
      .password(passwordEncoder.encode("123abcABC"))
      .roles(Set.of(role))
      .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
      .name(oAuth2UserInfo.getName())
      .email(oAuth2UserInfo.getEmail())
      .status(User.UserStatus.ACTIVE)
      .build();
    long start = System.nanoTime();
    User userSaved = userRepo.save(user);
    long end = System.nanoTime();
    long timeTaken = end - start;
    log.debug("Time save user: {} milliseconds", timeTaken/ 1_000_000);
    log.debug("Created user: {}", userSaved);
    return userSaved;
  }

  @Override
  @Transactional
  public Optional<User> getByEmailAndStatusUser(String email, User.UserStatus status) {

    if (userCacheService.exists(email)) {
      final User user = userCacheService.getUser(email);
      log.info("FindByEmailAndStatus() : cache >> " + user);
      Assert.notNull(user , "user cannot be null");
      return Optional.of(user);
    }
    final Optional<User> user = userRepo.findByEmailAndStatus(email, status);
    if(user.isPresent()) {
      userCacheService.getUser(email);
      log.info("FindByEmailAndStatus() : cache insert >> " + user.get());
    }
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsernameAndStatus(username, User.UserStatus.ACTIVE)
      .orElseThrow(() -> new ResourceNotFoundException("User","username", username));
    return UserDetailsCustom.build(user);
  }
}
