package com.supply.chain.mapper;

import com.supply.chain.dto.RoleDto;
import com.supply.chain.dto.UserDto;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    @DisplayName("mapToUserDto method")
    @Test
    public void givenUserObject_whenMapToUserDto_thenReturnUserDtoObject(){
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",true, roles);
        UserDto userDto = UserMapper.mapToUserDto(user);
        assertThat(userDto).isNotNull();
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getPassword()).isEqualTo(user.getPassword());
    }

    @DisplayName("mapToUser method")
    @Test
    public void givenUserDtoObject_whenMapToUser_thenReturnUserObject(){
        Set<RoleDto> roles = new HashSet<>();
        roles.add(new RoleDto(1,"ROLE_USER"));
        UserDto userDto = new UserDto(1,"bill","12345",roles);
        User user = UserMapper.mapToUser(userDto);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(userDto.getUsername());
    }
}
