package com.cfe.chat;

import com.cfe.chat.config.properties.ChatServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(ChatServerProperties.class)
@ConfigurationPropertiesScan("com.cfe.chat.config")
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

}
