package com.example.bespringgroovy.service.impl;

import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.repo.UserRepo;
import com.example.bespringgroovy.security.services.UserDetailsCustom;
import com.example.bespringgroovy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  @Autowired
  private UserRepo userRepo;
  @Override
  public Optional<User> getByUsernameAndStatus(String username, User.UserStatus status) {
    return userRepo.findByUsernameAndStatus(username, status);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsernameAndStatus(username, User.UserStatus.ACTIVE)
      .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
    return UserDetailsCustom.build(user);
  }
}
