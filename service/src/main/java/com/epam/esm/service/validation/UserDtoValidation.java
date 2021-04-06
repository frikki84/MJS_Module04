package com.epam.esm.service.validation;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.exception.UserValidationException;

@Component
public class UserDtoValidation {

    public static final boolean DEFAULT_RESULT = true;
    public static final String REGEX_EMAIL = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";

    public static final String INVALID_USER_NAME = "userdto_invalid_name";
    public static final String INVALID_EMAIL = "userdto_invalid_email";
    public static final String INVALID_PASSWORD = "userdto_invalid_password";
    public static final String USER_NAME_EXISTS = "username_exists";
    public static final int MIN_USER_LENGTH = 3;
    public static final int MAX_USER_LENGTH = 32;

    private final UserRepository userRepository;

    public UserDtoValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUserDto(UserDto userDto) {
        boolean result = true;
        chechUserDtoFormatName(userDto);
        List<User> userList = userRepository.findByName(userDto.getName());
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            throw new UserValidationException(USER_NAME_EXISTS);
        }
        chechUserEmail(userDto);
        chechUserDtoPassword(userDto);
        return result;
    }

    private boolean chechUserDtoFormatName(UserDto dto) {
        boolean result = DEFAULT_RESULT;
        if (dto.getName() == null || dto.getName().isBlank() || dto.getName().length() < MIN_USER_LENGTH
                || dto.getName().length() > MAX_USER_LENGTH) {
            throw new UserValidationException(INVALID_USER_NAME);
        }

        return result;
    }

    private boolean chechUserEmail(UserDto dto) {
        boolean result = DEFAULT_RESULT;
        if (Objects.isNull(dto.getEmail()) || dto.getPassword().isBlank()) {
            throw new UserValidationException(INVALID_EMAIL);
        }
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(dto.getEmail());
        if (!matcher.matches()) {
            throw new UserValidationException(INVALID_EMAIL);
        }
        return result;
    }

    private boolean chechUserDtoPassword(UserDto dto) {
        boolean result = DEFAULT_RESULT;
        if (dto.getPassword() == null || dto.getPassword().isBlank() || dto.getPassword().length() < MIN_USER_LENGTH
                || dto.getPassword().length() > MAX_USER_LENGTH) {
            throw new UserValidationException(INVALID_PASSWORD);
        }

        return result;
    }

}
