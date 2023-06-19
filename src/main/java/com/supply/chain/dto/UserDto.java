package com.supply.chain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer id;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 3, max = 20, message = "username must have between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 5, max = 20, message = "password must have between 5 and 20 characters")
    private String password;

    @NotEmpty(message = "the role/s must not be empty")
    private Set<RoleDto> roles;
}
