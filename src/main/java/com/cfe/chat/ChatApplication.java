package com.cfe.chat;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.format.DateTimeFormatter;

@EnableScheduling
@SpringBootApplication
//@EnableConfigurationProperties(ChatServerProperties.class)
@ConfigurationPropertiesScan("com.cfe.chat.config")
public class ChatApplication {

//	private static final String dateFormat = "yyyy-MM-dd";
//	private static final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZ";

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

//	@Bean
//	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//		return builder -> {
//			builder.simpleDateFormat(dateTimeFormat);
//			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
//			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
//		};
//	}
}
