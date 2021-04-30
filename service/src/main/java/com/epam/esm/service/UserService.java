package com.epam.esm.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.entity.ShownUserDto;
import com.epam.esm.entity.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.validation.UserDtoValidation;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.UserDtoMapper;
import com.epam.esm.service.validation.PageInfoValidation;
import com.epam.esm.service.validation.SecurityValidator;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper mapper;
    private final PageInfoValidation pageValidation;
    private final UserDtoValidation userDtoValidation;
    private final SecurityValidator securityValidator;

    public UserService(UserRepository userRepository, UserDtoMapper mapper, PageInfoValidation pageValidation,
            UserDtoValidation userDtoValidation, SecurityValidator securityValidator) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.pageValidation = pageValidation;
        this.userDtoValidation = userDtoValidation;
        this.securityValidator = securityValidator;
        pageValidation.setCrdOperations(userRepository);
    }


    public List<ShownUserDto> findAll(int offset, int limit) {
        pageValidation.checkPageInfo(offset, limit, CustomErrorCode.USER);
        return userRepository.findAll(offset, limit)
                .stream()
                .map(user -> mapper.chandeUserToDto(user))
                .collect(Collectors.toList());
    }


    public ShownUserDto findById(long id) {
        User user = userRepository.findById(id);
        if (Objects.isNull(user)) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        return mapper.chandeUserToDto(user);
    }


    public ShownUserDto create(UserDto entity) {
        userDtoValidation.checkUserDto(entity);
        User user = mapper.chandeDtoToUser(entity);
        User securityUser = securityValidator.findUserFromAuthentication();
        if (Objects.isNull(securityUser) || !securityUser.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.USER);
        }
        return mapper.chandeUserToDto(userRepository.create(user));
    }


    public long delete(long id) {
        Long findId;
        try {
            findId = userRepository.delete(id);
        } catch (RuntimeException e) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        return findId;
    }


    public long findNumberOfEntities() {
        return userRepository.findNumberOfEntities();
    }
}
