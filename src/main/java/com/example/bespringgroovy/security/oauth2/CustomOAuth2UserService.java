package com.example.bespringgroovy.security.oauth2;

import com.example.bespringgroovy.constant.RoleConstants;
import com.example.bespringgroovy.entity.AuthProvider;
import com.example.bespringgroovy.entity.Role;
import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.exception.OAuth2AuthenticationProcessingException;
import com.example.bespringgroovy.exception.ResourceNotFoundException;
import com.example.bespringgroovy.repo.RoleRepo;
import com.example.bespringgroovy.repo.UserRepo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.bespringgroovy.security.services.UserDetailsCustom;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Autowired
  private UserRepo userRepo;
  @Autowired
  private RoleRepo roleRepo;
  @Lazy
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
      .getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

    Assert.notNull(oAuth2UserInfo , "oAuth2UserInfo cannot be null");
    if(!StringUtils.hasLength(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<User> userOptional = userRepo.findByEmailAndStatus(oAuth2UserInfo.getEmail(), User.UserStatus.ACTIVE);
    User user;
    if(userOptional.isPresent()) {
      user = userOptional.get();
      if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
          user.getProvider() + " account. Please use your " + user.getProvider() +
          " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserDetailsCustom.build(user, oAuth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
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
    User userSaved = userRepo.save(user);
    log.debug("Created user: {}", userSaved);
    return userSaved;
  }

  private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setName(oAuth2UserInfo.getName());
//    existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    return userRepo.save(existingUser);
  }


}
