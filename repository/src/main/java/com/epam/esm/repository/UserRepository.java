package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;

public interface UserRepository extends CrdOperations<User>{
    public User findUserWithTheHighestCostOfAllOrder();



}
