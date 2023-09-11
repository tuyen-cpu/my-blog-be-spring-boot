package com.example.bespringgroovy.security.oauth2.user;

import com.example.bespringgroovy.entity.AuthProvider;
import com.example.bespringgroovy.exception.OAuth2AuthenticationProcessingException;
import com.example.bespringgroovy.security.oauth2.user.GithubOAuth2UserInfo;
import com.example.bespringgroovy.security.oauth2.user.OAuth2UserInfo;

import java.util.Map;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public class OAuth2UserInfoFactory {
  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
    if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
//      return new GoogleOAuth2UserInfo(attributes);
      return null;
    } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
//      return new FacebookOAuth2UserInfo(attributes);
      return null;
    } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
