package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;

public interface UserRepository {
    public List<User> findAll(int offset, int limit);
    public User findById(long id);
    public User create(User entity);
    public long delete(long id);
    public User findUserWithTheHighestCostOfAllOrder();
    public long findNumberOfEntities();

}
