package com.epam.esm.repository;

import com.epam.esm.entity.User;

public interface UserRepository extends CrdOperations<User> {

    public Long findUserWithTheHighestCostOfAllOrder();

    public User findByName(String userName);

}
