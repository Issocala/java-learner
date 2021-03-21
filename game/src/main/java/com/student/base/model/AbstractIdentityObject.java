package com.student.base.model;

import java.util.Objects;

/**
 * @author : luoyong
 * @date : 2021-03-21 10:54
 **/
public abstract class AbstractIdentityObject<K> implements IdentityObject<K> {
    protected K id;

    @Override
    public K getId() {
        return id;
    }

    @Override
    public void setId(K id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractIdentityObject<?> that = (AbstractIdentityObject<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
