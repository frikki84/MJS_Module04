package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SearchGiftCertificateParameter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.utils.GiftCertificateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    public static int OFFSET_DEFAULT_VALUE = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private final GiftCertificateCriteriaBuilder criteriaBuilder;

    public GiftCertificateRepositoryImpl(EntityManager entityManager, GiftCertificateCriteriaBuilder criteriaBuilder) {
        this.entityManager = entityManager;
        this.criteriaBuilder = criteriaBuilder;
    }


    @Override
    public List<GiftCertificate> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> giftCertificateCriteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = giftCertificateCriteriaQuery.from(GiftCertificate.class);
        giftCertificateCriteriaQuery.select(root);
        int itemsOffset = (offset - OFFSET_DEFAULT_VALUE) * limit;
        return entityManager.createQuery(giftCertificateCriteriaQuery)
                .setFirstResult(itemsOffset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findAll(SearchGiftCertificateParameter parametr, int offset, int limit) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.buildQuery(parametr);
        int itemsOffset = (offset - OFFSET_DEFAULT_VALUE) * limit;
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(itemsOffset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public GiftCertificate findById(long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public long delete(long id) {
        entityManager.refresh(findById(id));
        return id;
    }


    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public Long findNumberOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(GiftCertificate.class)));
        return entityManager.createQuery(query).getSingleResult();
    }




}

