package com.example.bespringgroovy.repo;

import com.example.bespringgroovy.entity.Role;
import org.springframework.context.annotation.Profile;
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
public interface RoleRepo extends JpaRepository<Role,Long> {

  Optional<Role> findByName(String name);
}
