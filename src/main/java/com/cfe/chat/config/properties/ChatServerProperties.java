package com.cfe.chat.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "chat-server")
public class ChatServerProperties {

    @NonNull
    private final String timeToLive;

    @NonNull
    private final Integer messageHistoryDays;
}
