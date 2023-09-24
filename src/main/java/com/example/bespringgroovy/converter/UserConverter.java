package com.example.bespringgroovy.converter;

import com.example.bespringgroovy.dto.UserDTO;
import com.example.bespringgroovy.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Component
public class UserConverter {
  @Autowired
  private ModelMapper modelMapper;

  public UserDTO convertToDto(User entity) {
    return modelMapper.map(entity, UserDTO.class);
  }

  public User convertToEntity(UserDTO dto) {
    return modelMapper.map(dto, User.class);
  }
}
