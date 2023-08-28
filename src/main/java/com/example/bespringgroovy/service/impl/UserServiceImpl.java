package com.example.bespringgroovy.service.impl;

import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.repo.UserRepo;
import com.example.bespringgroovy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepo userRepo;
  @Override
  public Optional<User> getByUsernameAndStatus(String username, User.UserStatus status) {
    return userRepo.findByUsernameAndStatus(username, status);
  }
}
