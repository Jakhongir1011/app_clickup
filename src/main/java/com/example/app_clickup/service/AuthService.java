package com.example.app_clickup.service;

import com.example.app_clickup.entity.User;
import com.example.app_clickup.entity.enums.SystemRoleName;
import com.example.app_clickup.payload.ApiResponse;
import com.example.app_clickup.payload.LoginDto;
import com.example.app_clickup.payload.RegisterDto;
import com.example.app_clickup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
    }

    public ApiResponse registerUserService(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail()))
            return new ApiResponse("email is available",false);

        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setSystemRoleName(SystemRoleName.SYSTEM_ROLE_USER);

        int code = new Random().nextInt(999999);
        String substring = String.valueOf(code).substring(0, 4);
        user.setEmailCode(passwordEncoder.encode(substring));
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Saved User", true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Account verification");
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public ApiResponse verifyEmailService(String email, String emailCode) {
// email code bormayakani uchun 76 qatorni != qildim
        Optional<User> optionUser = userRepository.findByEmail(email);
        if (optionUser.isPresent()){
            User user = optionUser.get();
            if (!emailCode.equals(user.getEmailCode())){
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("account success",true);
            }
            return new ApiResponse("emailCode error",false);
        }
        return new ApiResponse("User not found",false);
    }
}
