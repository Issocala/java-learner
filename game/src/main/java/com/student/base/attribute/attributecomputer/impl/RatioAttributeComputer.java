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
 * @date : 2021-03-20 20:12
 **/
public class RatioAttributeComputer implements AttributeComputer {

    private Collection<AttributeType> effectTypes;

    public RatioAttributeComputer(Collection<AttributeType> effectTypes) {
        this.effectTypes = effectTypes;
    }

    @Override
    public Collection<Attribute> compute(GameObject owner, AttributeType type, AttributeContainer container) {
        Collection<Attribute> results = new HashSet<>(effectTypes.size());
        for (AttributeType effectType : effectTypes) {
            long sourceValue = container.getAttributeValue(effectType);
            if (sourceValue == 0) {
                continue;
            }

            double ratioValue = container.getRatioValue(type);
            results.add(effectType.generateAttribute((long) (ratioValue * sourceValue)));
        }
        return results;
    }
}
