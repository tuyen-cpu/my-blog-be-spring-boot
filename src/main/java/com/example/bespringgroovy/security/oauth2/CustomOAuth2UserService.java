package com.example.bespringgroovy.security.oauth2;

import com.example.bespringgroovy.entity.AuthProvider;
import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.exception.OAuth2AuthenticationProcessingException;
import com.example.bespringgroovy.repo.UserRepo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.bespringgroovy.security.services.UserDetailsCustom;
import com.example.bespringgroovy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * CustomOAuth2UserService Class. <br>
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
  private UserService userService;

  @Override
  @Transactional
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

    Assert.notNull(oAuth2UserInfo, "oAuth2UserInfo cannot be null");
    if (!StringUtils.hasLength(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }
    long start = System.nanoTime();
    Optional<User> userOptional = userService.getByEmailAndStatusCheckCache(oAuth2UserInfo.getEmail(), User.UserStatus.ACTIVE);
    long end = System.nanoTime();
    long timeTaken = end - start;
    log.debug("Time select user: {} milliseconds", timeTaken / 1_000_000);

    User user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
          user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
      }
      if (user.getProvider().equals(AuthProvider.github)) {
        if (isChangeGithubInfo(user, oAuth2UserInfo)) {
          user = userService.updateUserOauth2(user, oAuth2UserInfo);
        }
      }
    } else {
      user = userService.addUserOauth2(oAuth2UserRequest, oAuth2UserInfo);
    }
    return UserDetailsCustom.build(user, oAuth2User.getAttributes());
  }

  private boolean isChangeGithubInfo(User user, OAuth2UserInfo oAuth2UserInfo) {
    return (Objects.equals(user.getEmail(), oAuth2UserInfo.getEmail())
      || Objects.equals(user.getName(), oAuth2UserInfo.getName()));
  }

}
