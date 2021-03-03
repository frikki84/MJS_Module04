package com.epam.esm.service;


import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> findAll(int offset, int limit);
    public UserDto findById(long id);
    public UserDto create(UserDto entity);
    public long delete(long id);
    public UserDto findUserWithTheHighestCostOfAllOrder();
    public long findNumberOfEntities();
}
