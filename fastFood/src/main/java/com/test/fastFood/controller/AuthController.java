package com.test.fastFood.controller;


import com.test.fastFood.dto.loginDTO.LoginResponseDto;
import com.test.fastFood.dto.loginDTO.LoginUserDto;
import com.test.fastFood.service.secureService.LoginManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/login")
@RequiredArgsConstructor
public class AuthController {
    private final LoginManagerService loginManagerService;

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginUserDto loginUserDto) {
        System.out.println(loginUserDto.getPassword());
        return loginManagerService.attemptLogin(loginUserDto.getUsername(), loginUserDto.getPassword());
    }
}
