package com.epam.esm.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;

@Repository
@Transactional
public class TagRepositoryImpl implements TagRepository {

    private String orderListAttribute = "orderList";
    private String certificateListAttribute = "giftCertificateList";
    private String tagAttribute = "tags";
    private String columnId = "id";
    private String columnName = "nameTag";
    private String sqlQueryFindAllMostUsedTags = "select t.nameTag from tag t join gift_certificate_has_tag gsht on gsht.tag_id_tag=t.id join gift_certificate gs on gsht.gift_certicicate_id_gift_certicicate=gs.id join users_order_has_certificate uohs on uohs.certificate_id=gs.id join users_order uo on uohs.order_id=uo.id where uo.user_id=?  group by t.nameTag having count(t.nametag) = (select count(t.nametag) from tag t  join gift_certificate_has_tag gsht on gsht.tag_id_tag=t.id join gift_certificate gs on gsht.gift_certicicate_id_gift_certicicate=gs.id join users_order_has_certificate uohs on uohs.certificate_id=gs.id join users_order uo on uohs.order_id=uo.id where uo.user_id=? group by t.nameTag order by count(t.nametag) desc limit 1) order by count(t.nametag) desc;";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tag> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> tagCriteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = tagCriteriaQuery.from(Tag.class);
        tagCriteriaQuery.select(root);
        return entityManager.createQuery(tagCriteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findByName(String tagName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> giftCertificateCriteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = giftCertificateCriteriaQuery.from(Tag.class);
        Predicate predicate = criteriaBuilder.like(root.get(columnName), tagName);
        giftCertificateCriteriaQuery.select(root).where(predicate);
        return entityManager.createQuery(giftCertificateCriteriaQuery).getResultList();
    }

    @Override
    public Tag create(Tag entity) {
        entityManager.persist(entity);
        entityManager.flush();
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

    @Override
    public List<Tag> getMostWidelyUsedUsersTag(long userId) {
        Query query = entityManager.createNativeQuery(sqlQueryFindAllMostUsedTags);
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        List<String> tagList = query.getResultList();
        List<Tag> result = new ArrayList<>();
        tagList.forEach(s -> result.add(findByName(s).get(0)));
        return result;

    }
}
