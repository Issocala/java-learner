package com.student.base.component.event.annotation;

import com.student.base.lifecycle.LifecycleOrder;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventOrder {
    LifecycleOrder value();
}
