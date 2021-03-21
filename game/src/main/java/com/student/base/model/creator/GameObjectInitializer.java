package com.student.base.model.creator;

import com.student.base.model.GameObject;

@FunctionalInterface
public interface GameObjectInitializer<T extends GameObject> {
    void init(T gameObject);
}
