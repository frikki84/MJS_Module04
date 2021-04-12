package com.epam.esm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SearchGiftCertificateParameter;
import com.epam.esm.entity.Tag;

@Component
public class GiftCertificateCriteriaBuilder {

    private String regexForCreationPredicates = "%";
    private String patternForEmptyCriteriaQuery = "";
    private String parameterName = "name";
    private String parameterDescription = "description";
    private String parameterTagNames = "tags";
    private String parameterSortbyName = "name";
    private String parameterSortbyCreateDate = "createDate";
    private String tagsInGiftcertificate = "tags";
    private String querySelectByTagName = "select tag from Tag tag where tag.nameTag in (:" + parameterTagNames + ")";

    @Autowired
    private final EntityManager entityManager;

    public GiftCertificateCriteriaBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CriteriaQuery<GiftCertificate> buildQuery(SearchGiftCertificateParameter parameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> giftCertificateCriteriaQuery = criteriaBuilder.createQuery(
                GiftCertificate.class);
        Root<GiftCertificate> root = giftCertificateCriteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();

        String parameterName = parameter.getName();

        if (Objects.nonNull(parameterName)) {
            String pattern = regexForCreationPredicates + parameterName + regexForCreationPredicates;
            Predicate predicate = criteriaBuilder.like(root.get(this.parameterName), pattern);
            predicateList.add(predicate);
        }

        String parameterDescription = parameter.getDescription();
        if (Objects.nonNull(parameterDescription)) {
            String pattern = regexForCreationPredicates + parameterDescription + regexForCreationPredicates;
            Predicate predicate = criteriaBuilder.like(root.get(this.parameterDescription), pattern);
            predicateList.add(predicate);
        }

        List<String> tagNames = parameter.getTags();

        if (Objects.nonNull(tagNames)) {
            List<String> distinctTagList = tagNames.stream().distinct().collect(Collectors.toList());
            List<Tag> tags = entityManager.createQuery(querySelectByTagName, Tag.class)
                    .setParameter(parameterTagNames, distinctTagList)
                    .getResultList();

            if (distinctTagList.size() != tags.size()) {
                return giftCertificateCriteriaQuery.select(root)
                        .where(criteriaBuilder.like(root.get(this.parameterName), patternForEmptyCriteriaQuery));
            }
            tags.forEach(tag -> predicateList.add(criteriaBuilder.isMember(tag, root.get(tagsInGiftcertificate))));
        }
        giftCertificateCriteriaQuery.select(root).where(predicateList.toArray(new Predicate[0]));
        SortParameter sortParameter = parameter.getSortBy();
        String sortValue = null;
        if (Objects.nonNull(sortParameter)) {
            switch (sortParameter) {
            case NAME:
                sortValue = parameterSortbyName;
                break;
            case CREATE_DATE:
                sortValue = parameterSortbyCreateDate;
                break;
            }
        }
        OrderType orderType = parameter.getOrder();
        Order order = null;
        if (Objects.nonNull(orderType)) {
            switch (orderType) {
            case ASC:
                order = criteriaBuilder.asc(root.get(sortValue));
                break;
            case DESC:
                order = criteriaBuilder.desc(root.get(sortValue));
                break;
            }

            giftCertificateCriteriaQuery.orderBy(order);
        }
        return giftCertificateCriteriaQuery;

    }
}
