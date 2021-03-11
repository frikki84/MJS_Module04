package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.CrdService;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.mapper.UserDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements CrdService<UserDto> {


    private final UserRepository userRepository;
    private final UserDtoMapper mapper;

    public UserService(UserRepository userRepository, UserDtoMapper mapper) {
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
        User user = userRepository.findById(id);
        if (Objects.isNull(user)) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        return mapper.chandeUserToDto(user);
    }

    @Override
    public UserDto create(UserDto entity) {
        return mapper.chandeUserToDto(userRepository.create(mapper.chandeDtoToUser(entity)));
    }

    @Override
    public long delete(long id) {
        Long findId;
        try {
            findId = userRepository.delete(id);
        } catch (RuntimeException e) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        return findId;
    }

    @Override
    public long findNumberOfEntities() {
        return userRepository.findNumberOfEntities();
    }
}
