package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

public interface TagRepository extends CrdOperations<Tag>{
    public Tag getMostWidelyUsedUsersTag(long userId);
}
