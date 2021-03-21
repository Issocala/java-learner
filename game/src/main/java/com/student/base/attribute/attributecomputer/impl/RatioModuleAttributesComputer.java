package com.student.base.attribute.attributecomputer.impl;

import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.type.AttributeType;
import com.student.base.model.GameObject;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author : luoyong
 * @date : 2021-03-20 20:22
 **/
public class RatioModuleAttributesComputer extends AbstractRatioModuleComputer {
    private final Collection<AttributeType> effectTypes;

    public RatioModuleAttributesComputer(ModuleType moduleType, Collection<AttributeType> effectTypes) {
        super(moduleType);
        this.effectTypes = effectTypes;
    }

    @Override
    public Collection<Attribute> compute(GameObject actor, AttributeType type, AttributeContainer container) {
        double ratioValue = container.getRatioValue(type);
        if (ratioValue == 0) {
            return null;
        }

        AttributeContainer effectContainer = getContainer(actor);
        if (effectContainer == null) {
            return null;
        }
        Collection<Attribute> results = new HashSet<>(effectTypes.size());
        for (AttributeType effectType : effectTypes) {
            long value = container.getAttributeValue(effectType);
            if (value == 0) {
                continue;
            }

            results.add(effectType.generateAttribute((long) (ratioValue * value)));
        }
        return results;
    }
}
