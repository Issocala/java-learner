package com.student.base.attribute.model.impl;

import com.student.base.attribute.type.AttributeType;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:43
 **/
public class LongAttribute extends AbstractAttribute {
    private long value;

    protected LongAttribute() {
    }

    public LongAttribute(AttributeType type, long value) {
        super(type);
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void updateValue(long value) {
        this.value = value;
    }
}
