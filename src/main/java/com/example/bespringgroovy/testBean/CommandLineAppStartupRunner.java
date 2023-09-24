package com.example.bespringgroovy.testBean;

import com.example.bespringgroovy.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
//@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
//  @Autowired
//  private RedisTemplate<String, String> template;
//  @Resource(name="redisTemplate")
//  private ListOperations<String, String> listOps;

  @Override
  public void run(String... args) throws Exception {
//    template.opsForValue().set("loda","hello world");
//
//    System.out.println("Value of key loda: "+template.opsForValue().get("loda"));
  }
}
