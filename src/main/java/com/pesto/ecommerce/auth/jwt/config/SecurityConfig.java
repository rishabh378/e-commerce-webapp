package com.pesto.ecommerce.auth.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security")
public class SecurityConfig {

    private List<String> allowedPublicApis;
}
