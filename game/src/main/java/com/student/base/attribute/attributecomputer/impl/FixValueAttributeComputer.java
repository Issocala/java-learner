package com.student.base.attribute.attributecomputer.impl;

import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.type.AttributeType;
import com.student.base.model.GameObject;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author : luoyong
 * @date : 2021-03-20 20:05
 **/
public class FixValueAttributeComputer implements AttributeComputer {

    private Collection<AttributeType> effectTypes;

    public FixValueAttributeComputer(Collection<AttributeType> effectTypes) {
        this.effectTypes = effectTypes;
    }

    @Override
    public Collection<Attribute> compute(GameObject owner, AttributeType type, AttributeContainer container) {
        long addValue = container.getAttributeValue(type);
        if (addValue == 0) {
            return null;
        }

        Collection<Attribute> results = new HashSet<>(effectTypes.size());
        for (AttributeType effectType : effectTypes) {
            results.add(effectType.generateAttribute(addValue));
        }

        return results;
    }

}
