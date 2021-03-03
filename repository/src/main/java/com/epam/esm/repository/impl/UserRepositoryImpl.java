package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
   public static final int OFFSET_DEFAULT_VALUE = 1;
   public static final String QUERY_FIND_USER_WITH_HIGHIEST_COST_OF_ORDERS = "select users_order.user_id from users_order group by users_order.user_id order by sum(users_order.price) desc;";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(root);
        int itemsOffset = (offset - OFFSET_DEFAULT_VALUE) * limit;
        return entityManager.createQuery(userCriteriaQuery)
                .setFirstResult(itemsOffset)
                .setMaxResults(limit)
                .getResultList();
    }


    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User create(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public long delete(long id) {
        entityManager.remove(findById(id));
        return id;
    }

    @Override
    public long findNumberOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(User.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public User findUserWithTheHighestCostOfAllOrder() {
        return (User) entityManager.createQuery(QUERY_FIND_USER_WITH_HIGHIEST_COST_OF_ORDERS)
                .setMaxResults(1).getSingleResult();
    }


}
