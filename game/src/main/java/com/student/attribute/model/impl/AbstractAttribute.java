package com.student.attribute.model.impl;


import com.student.attribute.model.Attribute;
import com.student.attribute.type.AttributeType;

/**
 * @author : luoyong
 * @date : 2021-03-20 19:28
 **/
public abstract class AbstractAttribute implements Attribute {
    protected AttributeType type;

    protected AbstractAttribute() {
    }

    public AbstractAttribute(AttributeType type) {
        this.type = type;
    }

    @Override
    public AttributeType getType() {
        return type;
    }
}
