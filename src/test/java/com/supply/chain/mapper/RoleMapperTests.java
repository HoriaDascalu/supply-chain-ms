package com.supply.chain.mapper;

import com.supply.chain.dto.RoleDto;
import com.supply.chain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RoleMapperTests {

    @DisplayName("mapToRoleDto method")
    @Test
    public void givenRoleObject_whenMapToRoleDto_thenReturnRoleDtoObject(){
        Role role = new Role("ROLE_USER");
        RoleDto roleDto = RoleMapper.mapToRoleDto(role);

        assertThat(roleDto).isNotNull();
        assertThat(roleDto.getName()).isEqualTo(role.getName());
    }

    @DisplayName("mapToRole method")
    @Test
    public void givenRoleDtoObject_whenMapToRole_thenReturnRoleObject(){
        RoleDto roleDto = new RoleDto(1,"ROLE_USER");
        Role role = RoleMapper.mapToRole(roleDto);

        assertThat(role).isNotNull();
        assertThat(role.getName()).isEqualTo(roleDto.getName());
    }
}
