package com.example.bespringgroovy.security.services;

import com.example.bespringgroovy.entity.User;
import com.example.bespringgroovy.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
public class UserDetailsImpl implements UserDetailsService {
@Autowired
private UserRepo userRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsernameAndStatus(username, User.UserStatus.ACTIVE)
      .orElseThrow(()->new UsernameNotFoundException("Not found user with username: "+ username));
    return UserDetailsCustom.build(user);
  }
  private List<SimpleGrantedAuthority> getAuthorities(User user){
    return user.getRoles().stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .toList();
  }
}
