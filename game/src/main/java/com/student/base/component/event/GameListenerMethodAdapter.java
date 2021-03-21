package com.student.base.component.event;

import com.student.base.component.event.annotation.EventOrder;
import com.student.base.lifecycle.LifecycleOrder;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.Objects;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:31
 **/
public class GameListenerMethodAdapter implements GameListener, Ordered {

    private final Object target;

    private final Method targetMethod;

    private final Class<? extends GameEvent> eventType;

    private int order;

    public GameListenerMethodAdapter(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
        this.order = resolveOrder(this.targetMethod);
        this.eventType = resolveEventType(this.targetMethod);
    }


    private int resolveOrder(Method targetMethod) {
        EventOrder ann = AnnotatedElementUtils.findMergedAnnotation(targetMethod, EventOrder.class);

        return ann != null ? ann.value().getOrder() : LifecycleOrder.NORMAL.getOrder();
    }

    private Class<? extends GameEvent> resolveEventType(Method targetMethod) {
        int count = targetMethod.getParameterCount();
        if (count > 1) {
            throw new IllegalStateException("Maximum one parameter is allowed for event listener method: " + targetMethod);
        }

        if (count == 0) {
            throw new IllegalStateException("Event patameter is mandatory for event listener method: " + targetMethod);
        } else {
            Class<?> parameterType = targetMethod.getParameterTypes()[0];
            if (!(GameEvent.class.isAssignableFrom(parameterType))) {
                throw new IllegalStateException(
                        "Event parameter must inherited from GameEvent for event listener method:" + targetMethod);
            }
            return (Class<? extends GameEvent>) parameterType;
        }
    }

    @Override
    public void onGameEvent(GameEvent event) {
        ReflectionUtils.makeAccessible(this.targetMethod);
        try {
            targetMethod.invoke(target, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<? extends GameEvent> getEventType() {
        return this.eventType;
    }

    @Override
    public int compareTo(Object o) {
        GameListenerMethodAdapter target = (GameListenerMethodAdapter) o;
        if (this.getOrder() > target.getOrder()) {
            return 1;
        }
        if (this.getOrder() < target.getOrder()) {
            return -1;
        }
        if (this.equals(target)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameListenerMethodAdapter that = (GameListenerMethodAdapter) o;

        return Objects.equals(target, that.target) &&
                Objects.equals(targetMethod, that.targetMethod);
    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (targetMethod != null ? targetMethod.hashCode() : 0);
        return result;
    }
}
