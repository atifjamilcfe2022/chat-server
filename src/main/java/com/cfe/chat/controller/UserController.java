package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.UserDto;
import com.cfe.chat.controller.mapper.UserMapper;
import com.cfe.chat.controller.response.UserResponse;
import com.cfe.chat.domain.User;
import com.cfe.chat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))})})
    @GetMapping
    private ResponseEntity<?> users() {
        log.debug("getting users");
        List<User> users = userService.getUsers();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(userMapper.toUserDto(user)));
        log.info("users found :{}", userDtos.size());
        return ResponseEntity.ok(UserResponse.builder().count(userDtos.size()).users(userDtos).build());
    }

    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content)})
    @GetMapping("/{userId}")
    private ResponseEntity<?> user(@Parameter(description = "id of user to be searched")
                                        @PathVariable Long userId) {
        log.debug("getting user by Id: {}", userId);
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }
}
