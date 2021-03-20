package com.student.attribute.model.impl;

import com.student.attribute.type.AttributeType;

/**
 * @author : luoyong
 * @date : 2021-03-20 19:27
 **/
public class IntegerAttribute extends AbstractAttribute {
    private int value;

    protected IntegerAttribute() {
    }

    public IntegerAttribute(AttributeType type, long value) {
        super(type);
        this.value = (int) value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void updateValue(long value) {
        this.value = (int) value;
    }
}
