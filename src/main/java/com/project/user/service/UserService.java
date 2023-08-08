package com.project.user.service;

import com.project.user.dto.UserRequestDto;
import com.project.user.dto.UserRequestDto.DeleteUser;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    boolean isDuplicatedUsername(String username);

    void registerUser(UserRequestDto.RegisterUser param) throws NoSuchAlgorithmException;

    void changePassword(UserRequestDto.ChangePassword param) throws NoSuchAlgorithmException;

    void deleteUser(DeleteUser param);
}