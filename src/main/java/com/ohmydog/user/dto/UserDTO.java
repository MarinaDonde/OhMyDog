package com.ohmydog.user.dto;

import com.ohmydog.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class UserDTO {
    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    private UUID id;
    private String email;
    private String username;
}
