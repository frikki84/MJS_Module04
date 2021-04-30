package com.epam.esm.service.mapper;

import com.epam.esm.entity.ShownUserDto;
import com.epam.esm.entity.UserDto;
import com.epam.esm.entity.User;

public interface UserDtoMapper {

    public User chandeDtoToUser(UserDto dto);

    public ShownUserDto chandeUserToDto(User user);

    public User changeShownDtoToUser(ShownUserDto dto);

    public  UserDto changeShownToSimpleDto(ShownUserDto shownUserDto);

}
