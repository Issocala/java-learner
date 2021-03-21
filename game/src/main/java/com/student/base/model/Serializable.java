package com.student.base.model;

public interface Serializable<T> {
    T serialize();

    void deserialize(T data);
}

