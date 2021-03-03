package com.epam.esm.service.impl;

import com.epam.esm.entity.UserDto;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.mapper.UserDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public List<UserDto> findAll(int offset, int limit) {
        return userRepository.findAll(offset, limit).stream()
                .map(user -> mapper.chandeUserToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long id) {
        return mapper.chandeUserToDto(userRepository.findById(id));
    }

    @Override
    public UserDto create(UserDto entity) {
        return mapper.chandeUserToDto(userRepository.create(mapper.chandeDtoToUser(entity)));
    }

    @Override
    public long delete(long id) {
        return userRepository.delete(id);
    }

    @Override
    public UserDto findUserWithTheHighestCostOfAllOrder() {
        return mapper.chandeUserToDto(userRepository.findUserWithTheHighestCostOfAllOrder());
    }

    @Override
    public long findNumberOfEntities() {
        return userRepository.findNumberOfEntities();
    }
}
