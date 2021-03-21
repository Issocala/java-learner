package com.student.base.component.event.annotation;

import java.lang.annotation.*;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:19
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GameEventListener {
}
