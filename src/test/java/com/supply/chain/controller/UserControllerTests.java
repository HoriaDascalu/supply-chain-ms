package com.supply.chain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.chain.dto.UserDto;
import com.supply.chain.mapper.UserMapper;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import com.supply.chain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class UserControllerTests {

    private MockMvc mvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    private User user;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
        objectMapper = new ObjectMapper();

        Set<Role> roles = Set.of(new Role("ROLE_USER"));
        user = new User(1,"bill","12345",true, roles);
        userDto = UserMapper.mapToUserDto(user);

    }

    @DisplayName("saveUser endpoint")
    @Test
    public void givenUserObject_whenSaveObject_thenReturnSavedObject() throws Exception {

        given(userService.saveUser(userDto)).willReturn(userDto);

        MvcResult result = this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserDto savedUser = objectMapper.readValue(json, UserDto.class);

        assertThat(savedUser.getUsername()).isEqualTo(userDto.getUsername());
    }
}
