package com.student.base.attribute.container.impl;

import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.type.AttributeType;

import java.util.Collection;
import java.util.Map;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:36
 **/
public class ImmutableAttributeContainer extends SimpleAttributeContainer {
    public ImmutableAttributeContainer() {
    }

    public ImmutableAttributeContainer(Map<AttributeType, Attribute> attributeMap) {
        super(attributeMap);
    }

    public ImmutableAttributeContainer(Collection<Attribute> attributes) {
        super(attributes);
    }

    public ImmutableAttributeContainer(AttributeContainer container) {
        super(container);
    }


}
