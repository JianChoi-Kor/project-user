package com.project.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

    @Getter
    @Setter
    public static class RegisterUser {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
        @NotBlank(message = "성함을 입력해주세요.")
        private String name;
        @NotNull(message = "나이를 입력해주세요.")
        private Integer age;
    }

    @Getter
    @Setter
    public static class ChangePassword {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;
        @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
        private String changePassword;
    }

    @Getter
    @Setter
    public static class DeleteUser {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;
    }
}
