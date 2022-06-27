package com.example.app_clickup.controller;

import com.example.app_clickup.entity.User;
import com.example.app_clickup.payload.ApiResponse;
import com.example.app_clickup.payload.LoginDto;
import com.example.app_clickup.payload.RegisterDto;
import com.example.app_clickup.security.JwtProvider;
import com.example.app_clickup.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

      @Autowired
      AuthService authService;

      @Autowired
      AuthenticationManager authenticationManager;

      @Autowired
      JwtProvider jwtProvider;

      @PostMapping("/register")
      public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto){

          ApiResponse apiResponse = authService.registerUserService(registerDto);
          return ResponseEntity.status(apiResponse.isSuccess()?201:407).body(registerDto.getEmail());
      }

      @PostMapping("/login")
      public HttpEntity loginUser(@Valid @RequestBody LoginDto loginDto){
          try{
              Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                      loginDto.getEmail(),
                      loginDto.getPassword()
              ));
              User user = (User) authenticate.getPrincipal();
              String token = JwtProvider.generateToken(user.getEmail());
                  return ResponseEntity.ok(token);

          }catch (Exception e){
              return ResponseEntity.status(407).body(new ApiResponse("password or username error!",false));
          }
      }

      @PutMapping("/verifyEmail")
      public HttpEntity verifyEmail(@RequestParam String email, @RequestParam String emailCode){
         ApiResponse apiResponse =  authService.verifyEmailService(email,emailCode);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
      }



}
