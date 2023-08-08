package com.project.user.service;

import com.project.user.dto.UserRequestDto;
import com.project.user.dto.UserRequestDto.DeleteUser;
import com.project.user.entity.User;
import com.project.user.repository.UserRepository;
import com.project.user.utils.Helper;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  /**
   * 동일한 아이디로 가입된 회원이 있는지 확인
   *
   * @param username - 회원 아이디
   * @return boolean - 중복 여부
   */
  @Override
  public boolean isDuplicatedUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  /**
   * 회원 가입
   *
   * @param param - 회원 가입 정보
   */
  @Transactional
  @Override
  public void registerUser(UserRequestDto.RegisterUser param) throws NoSuchAlgorithmException {
    if (isDuplicatedUsername(param.getUsername())) {
      throw new IllegalStateException("동일한 아이디로 가입된 사용자가 이미 존재합니다.");
    }

    User user = User.builder()
        .username(param.getUsername())
        .password(Helper.hashPassword(param.getPassword()))
        .name(param.getName())
        .age(param.getAge())
        .build();
    userRepository.save(user);
  }

  /**
   * 회원 비밀번호 변경
   *
   * @param param - 회원 비밀번호 변경 정보
   */
  @Transactional
  @Override
  public void changePassword(UserRequestDto.ChangePassword param) throws NoSuchAlgorithmException {
    User targetUser = userRepository.findByUsername(param.getUsername())
        .orElseThrow(() -> new IllegalStateException("해당하는 아이디로 가입된 사용자가 존재하지 않습니다."));

    targetUser.changePassword(Helper.hashPassword(param.getChangePassword()));
    userRepository.save(targetUser);
  }

  /**
   * 회원 정보 삭제
   *
   * @param param - 회원 삭제 정보
   */
  @Transactional
  @Override
  public void deleteUser(DeleteUser param) {
    User targetUser = userRepository.findByUsername(param.getUsername())
        .orElseThrow(() -> new IllegalStateException("해당하는 아이디로 가입된 사용자가 존재하지 않습니다."));

    userRepository.delete(targetUser);
  }
}