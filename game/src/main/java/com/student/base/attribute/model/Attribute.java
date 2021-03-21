package com.student.base.attribute.model;

import com.student.base.attribute.type.AttributeType;

/**
 * 属性，接口都使用long
 */
public interface Attribute {
    /**
     * 取值
     */
    long getValue();

    /**
     * 更新值
     *
     * @param value 新值
     */
    void updateValue(long value);

    /**
     * 获取类型
     *
     * @return
     */
    AttributeType getType();

    default void alter(long value) {
        getType().getAlterMethod().alter(this, value);
    }
}
