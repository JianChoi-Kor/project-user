package com.project.user.controller;

import com.project.user.dto.UserRequestDto;
import com.project.user.dto.UserRequestDto.DeleteUser;
import com.project.user.dto.UserResponseDto.CheckUsernameResponse;
import com.project.user.service.UserService;
import com.project.user.utils.ApiResponse;
import com.project.user.utils.ApiResponse.FieldError;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ApiResponse apiResponse;

    private final UserService userService;

    /**
     * 생성하고자 하는 ID가 있는지 중복 확인
     *
     * @param username - 회원 아이디
     * @return CheckUsernameResponse - 아이디 사용 가능 여부 반환 객체
     */
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        if (!StringUtils.hasText(username)) {
            FieldError fieldError = new FieldError("username", "아이디를 입력해주세요.");
            return apiResponse.fail(null, Collections.singletonList(fieldError));
        }

        boolean isDuplicated = userService.isDuplicatedUsername(username);

        return apiResponse.success(new CheckUsernameResponse(!isDuplicated));
    }

    /**
     * 회원의 아이디와 비밀번호, 성명, 나이를 입력 받아 회원 테이블에 저장
     *
     * @param param - 회원 가입 정보
     * @param bindingResult - validation error
     */
    @PostMapping(value = "/add-username", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUsername(@RequestBody @Validated UserRequestDto.RegisterUser param, BindingResult bindingResult)
        throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return apiResponse.fail(bindingResult);
        }
        userService.registerUser(param);

        return apiResponse.success();
    }

    /**
     * 회원 비밀번호를 수정하기 위해 아이디와 변경하고자 하는 비밀번호를 입력 받아 비밀번호 변경
     *
     * @param param - 회원 비밀번호 변경 정보
     * @param bindingResult - validation error
     */
    @PutMapping(value = "/change-pw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody @Validated UserRequestDto.ChangePassword param, BindingResult bindingResult)
        throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return apiResponse.fail(bindingResult);
        }
        userService.changePassword(param);

        return apiResponse.success();
    }

    /**
     * 회원 아이디를 입력 받아 해당 회원 정보 삭제
     *
     * @param param - 회원 삭제 정보
     * @param bindingResult - validation error
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Validated DeleteUser param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return apiResponse.fail(bindingResult);
        }
        userService.deleteUser(param);

        return apiResponse.success();
    }
}