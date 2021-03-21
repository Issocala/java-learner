package com.student.base.model;

/**
 * <p>
 * java默认的clone方法，即使之类没有重写clone也可以进行，
 * 容易出现明明需要深拷贝，但其实子类并没有实现，所以这边提供Copyable接口,可以结合拷贝构造函数完成深拷贝
 * </p>
 *
 * @author : luoyong
 * @date : 2021-03-21 00:03
 **/
public interface Copyable<T> {
    T copy();
}
