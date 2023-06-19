package com.supply.chain.service;

import com.supply.chain.dto.UserDto;
import com.supply.chain.mapper.UserMapper;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import com.supply.chain.repository.UserRepository;
import com.supply.chain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;


    @BeforeEach
    public void setup(){
        Set<Role> roles = Set.of(new Role("ROLE_USER"));
        user = new User(1,"bill","12345",true, roles);
        userDto = UserMapper.mapToUserDto(user);
    }


    @DisplayName("saveUser method")
    @Test
    public void givenUserObject_whenSaveObject_thenReturnSavedObject(){
        //given
        given(userRepository.save(Mockito.any())).willReturn(user);
        //when
        UserDto savedUser = userService.saveUser(userDto);

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(userDto.getUsername());

    }
}
