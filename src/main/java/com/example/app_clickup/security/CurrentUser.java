package com.example.app_clickup.security;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal // getContext getPrinciple ni olib beradigan
public @interface CurrentUser {
    // buni controllerda ishlatganimiz ma'qul
}
