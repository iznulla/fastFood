package com.test.fastFood.dto.loginDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private final String accessToken;
}
