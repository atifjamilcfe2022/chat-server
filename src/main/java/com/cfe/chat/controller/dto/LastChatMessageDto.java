package com.cfe.chat.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastChatMessageDto {

    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String messageBody;
    private Long userId;
    private String userName;
    private Integer unreadMessageCount;
}
