package com.student.base.model;

public interface Mergeable<T> {
    boolean merge(T other);
}
