package com.cfe.chat.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("No")
    private Long id;
    @JsonProperty("userName")
    private String username;
    @JsonProperty("Full_Name")
    private String fullName;
    private String email;
}
