package com.example.bespringgroovy.service;

import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Optional;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public interface UserService {
  User getById(Long id);
  Optional<User> getByUsernameAndStatus(String username, User.UserStatus status);

  User updateUserOauth2(User existingUser, OAuth2UserInfo oAuth2UserInfo);

  User addUserOauth2(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);

  Optional<User> getByEmailAndStatusCheckCache(String email, User.UserStatus status);

  User getByEmailAndStatus(String email, User.UserStatus status);
}
