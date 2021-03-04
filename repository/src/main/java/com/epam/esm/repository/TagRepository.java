package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagRepository extends CrdOperations<Tag>{
    public Tag getMostWidelyUsedUsersTag(long userId);
    public List<Tag> findByName(String tagName);
}
