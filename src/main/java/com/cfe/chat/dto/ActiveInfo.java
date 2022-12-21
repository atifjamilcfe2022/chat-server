package com.cfe.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveInfo {

    private Long userId;
    private boolean alive;
    private LocalDateTime pingTime = LocalDateTime.now();
}
