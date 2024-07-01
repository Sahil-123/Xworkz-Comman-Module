package com.xworkz.repository;

import com.xworkz.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Boolean save(UserDTO userDTO);

    Optional<List<UserDTO>> findByUserMail(String mail);

    Optional<List<UserDTO>> findByUserMobile(String mobile);

    boolean updatePassword(UserDTO userDTO,String password);

    boolean updateByDto(UserDTO userDTO);

    Optional<List<UserDTO>> getAllUsers();

    boolean merge(UserDTO userDTO);

    boolean checkMobile(String mobile);

    boolean checkEmail(String email);

}
