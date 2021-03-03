package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tag> findAll(int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> tagCriteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = tagCriteriaQuery.from(Tag.class);
        tagCriteriaQuery.select(root);
        return entityManager.createQuery(tagCriteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag create(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public long delete(long id) {
        entityManager.remove(findById(id));
        return id;
    }

    @Override
    public Long findNumberOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Tag.class)));
        return entityManager.createQuery(query).getSingleResult();
    }
}
