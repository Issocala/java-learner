package com.student.attribute.altermethod;

import com.student.attribute.model.Attribute;

/**
 * 属性叠加方式
 */
public interface AttributeAlterMethod {
    /**
     * 叠加
     *
     * @param attribute
     * @param value
     */
    void alter(Attribute attribute, long value);
}
