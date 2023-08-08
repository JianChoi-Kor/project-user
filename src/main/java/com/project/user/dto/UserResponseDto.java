package com.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserResponseDto {

    @Getter
    @AllArgsConstructor
    public static class CheckUsernameResponse {
        private boolean usable;
    }
}
