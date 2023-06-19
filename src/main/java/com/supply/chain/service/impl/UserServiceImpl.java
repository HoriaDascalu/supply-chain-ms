package com.supply.chain.service.impl;

import com.supply.chain.dto.RoleDto;
import com.supply.chain.dto.UserDto;
import com.supply.chain.mapper.UserMapper;
import com.supply.chain.model.User;
import com.supply.chain.repository.UserRepository;
import com.supply.chain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        Set<RoleDto> roles = userDto.getRoles();
        Set<Integer> roleNames = new HashSet<>();

        for(RoleDto role:roles){
            roleNames.add(role.getId());
        }
        User user = UserMapper.mapToUser(userDto);
        if(roleNames.contains(1)){
            user.setCustomer(true);
        }
        return UserMapper.mapToUserDto(userRepository.save(user));
    }
}
