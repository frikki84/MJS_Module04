package com.epam.esm.service.validation;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.epam.esm.configuration.IntParameterValues;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;
import com.epam.esm.service.exception.UserValidationException;

@Component
public class UserDtoValidation {

    private String regexForEmail = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";

    private final UserRepository userRepository;

    public UserDtoValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkUserDto(UserDto userDto) {
        chechUserDtoFormatName(userDto);
        List<User> userList = userRepository.findByName(userDto.getName());
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            throw new UserValidationException(LocalizationExceptionMessageValues.USER_NAME_EXISTS.getMessage());
        }
        chechUserEmail(userDto);
        chechUserDtoPassword(userDto);

    }

    private void chechUserDtoFormatName(UserDto dto) {
        if (Objects.isNull(dto.getName() ) || dto.getName().isBlank()
                || dto.getName().length() < IntParameterValues.MIN_USER_NAME_LENGTH.getValue()
                || dto.getName().length() > IntParameterValues.MAX_USER_NAME_LENGTH.getValue()) {
            throw new UserValidationException(LocalizationExceptionMessageValues.INVALID_USER_NAME.getMessage());
        }

    }

    private void chechUserEmail(UserDto dto) {
        if (Objects.isNull(dto.getEmail()) || dto.getEmail().isBlank()) {
            throw new UserValidationException(LocalizationExceptionMessageValues.INVALID_EMAIL.getMessage());
        }
        Pattern pattern = Pattern.compile(regexForEmail);
        Matcher matcher = pattern.matcher(dto.getEmail());
        if (!matcher.matches()) {
            throw new UserValidationException(LocalizationExceptionMessageValues.INVALID_EMAIL.getMessage());
        }
    }

    private void chechUserDtoPassword(UserDto dto) {
        if (Objects.isNull(dto.getPassword()) || dto.getPassword().isBlank()
                || dto.getPassword().length() < IntParameterValues.MIN_USER_NAME_LENGTH.getValue()
                || dto.getPassword().length() > IntParameterValues.MAX_USER_NAME_LENGTH.getValue()) {
            throw new UserValidationException(LocalizationExceptionMessageValues.INVALID_PASSWORD.getMessage());
        }

    }

}
