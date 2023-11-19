package com.sparta.springtodolist.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "할 일 카드 API 목록", version = "1.0"))
public class SwaggerConfig {

}
