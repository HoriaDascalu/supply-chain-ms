package com.supply.chain.mapper;

import com.supply.chain.dto.UserDto;
import com.supply.chain.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static User mapToUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .roles(userDto.getRoles().stream().map(RoleMapper::mapToRole).collect(Collectors.toSet()))
                .build();
    }

    public static UserDto mapToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(RoleMapper::mapToRoleDto).collect(Collectors.toSet()))
                .build();
    }
}
