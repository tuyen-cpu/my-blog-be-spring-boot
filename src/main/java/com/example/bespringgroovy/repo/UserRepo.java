package com.example.bespringgroovy.repo;

import com.example.bespringgroovy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

  Optional<User> findByUsernameAndStatus(String userName, User.UserStatus status);

}
