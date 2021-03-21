package com.student.base.component;

import com.student.base.component.event.GameEvent;
import com.student.base.model.GameObject;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:07
 **/
public abstract class AbstractComponent<E extends GameObject> implements Component<E> {

    protected transient E owner;

    public AbstractComponent() {
    }

    public AbstractComponent(E owner) {
        this.owner = owner;
    }

    @Override
    public E getOwner() {
        return owner;
    }

    public void publishEvent(GameEvent gameEvent) {
        owner.getComponentContainer().publishEvent(gameEvent);
    }

    @Override
    public void init(E owner) {
        this.owner = owner;
    }
}
