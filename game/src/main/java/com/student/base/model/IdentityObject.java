package com.student.base.model;

public interface IdentityObject<K> {
    K getId();

    void setId(K id);
}
