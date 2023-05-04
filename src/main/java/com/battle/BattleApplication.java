package com.battle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@SpringBootApplication
//@OpenAPIDefinition(info = @Info(title = "Battle API",version = "2.0",description = "Battle App Service"))
//@SecurityScheme(name = "Battle-api", scheme = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
//@SecurityScheme(
//  name = "Bearer Authentication",
//  type = SecuritySchemeType.HTTP,
//  bearerFormat = "JWT",
//  scheme = "bearer"
//)
public class BattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleApplication.class, args);
	}

}
