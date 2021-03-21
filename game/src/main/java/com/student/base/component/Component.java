package com.student.base.component;

import com.student.base.model.GameObject;

public interface Component<E extends GameObject> {
    E getOwner();

    /**
     * 默认是组件class
     *
     * @return
     */
    default Object getType() {
        return this.getClass();
    }

    default void init(E owner) {

    }

    default void destroy() {
    }
}
