package com.example.bespringgroovy.repo;

import com.example.bespringgroovy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public interface UserRepo extends JpaRepository<User,Long> {

//  Optional<User> findAllBy


}
