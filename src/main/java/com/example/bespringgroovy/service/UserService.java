package com.example.bespringgroovy.service;

import com.example.bespringgroovy.entity.User;

import java.util.Optional;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public interface UserService {
  Optional<User> getByUsernameAndStatus(String username, User.UserStatus status);

}
