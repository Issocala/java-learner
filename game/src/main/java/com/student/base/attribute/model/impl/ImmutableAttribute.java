package com.student.base.attribute.model.impl;

import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.type.AttributeType;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:07
 **/
public final class ImmutableAttribute extends AbstractAttribute {
    private long value;

    protected ImmutableAttribute() {
    }

    public ImmutableAttribute(AttributeType type, long value) {
        super(type);
        this.value = value;
    }

    public ImmutableAttribute(Attribute attribute) {
        super(attribute.getType());
        this.value = attribute.getValue();
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void updateValue(long value) {
        throw new UnsupportedOperationException();
    }
}
