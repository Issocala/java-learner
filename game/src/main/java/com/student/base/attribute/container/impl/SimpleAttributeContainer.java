package com.student.base.attribute.container.impl;

import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.model.impl.ImmutableAttribute;
import com.student.base.attribute.type.AttributeType;

import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:04
 **/
public class SimpleAttributeContainer implements AttributeContainer {

    protected final Map<AttributeType, Attribute> attributeMap;

    public SimpleAttributeContainer() {
        this.attributeMap = new HashMap<>();
    }

    public SimpleAttributeContainer(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public SimpleAttributeContainer(Collection<Attribute> attributes) {
        this(new HashMap<>(attributes.size()));
        for (Attribute attribute : attributes) {
            this.attributeMap.put(attribute.getType(), new ImmutableAttribute(attribute));
        }
    }

    public SimpleAttributeContainer(AttributeContainer container) {
        this(container.getAllAttribute());
    }


    @Override
    public long getAttributeValue(AttributeType type) {
        Attribute attribute = attributeMap.get(type);
        return attribute == null ? 0 : attribute.getValue();
    }

    @Override
    public double getRatioValue(AttributeType type) {
        return getAttributeValue(type) / AttributeType.ATTRIBUTE_RATIO;
    }

    @Override
    public Attribute getAttribute(AttributeType type) {
        return attributeMap.get(type);
    }

    @Override
    public Attribute getOrCreateAttribute(AttributeType type) {
        return attributeMap.computeIfAbsent(type, k -> k.generateAttribute(0));
    }

    @Override
    public Collection<Attribute> getAllAttribute() {
        return attributeMap.values();
    }

    @Override
    public Collection<AttributeType> getAllType() {
        return attributeMap.keySet();
    }

    @Override
    public Collection<AttributeType> accumulate(Collection<AttributeContainer> containers, Set<ModuleType> changeModules) {
        resetValue();
        for (AttributeContainer container : containers) {
            addAttribute(container.getAllAttribute());
        }
        // SimpleAttributeContainer不收集changeTyS,在根节点统一收集
        return Collections.emptyList();
    }

    @Override
    public void clear() {
        attributeMap.clear();
    }

    @Override
    public void resetValue() {
        for (Attribute value : attributeMap.values()) {
            value.updateValue(0);
        }
    }

    @Override
    public void addAttribute(Attribute attribute) {
        Attribute attribute1 = getOrCreateAttribute(attribute.getType());
        attribute1.alter(attribute.getValue());
    }

    @Override
    public void addAttribute(AttributeType type, long value) {
        Attribute attribute1 = getOrCreateAttribute(type);
        attribute1.alter(value);
    }

    @Override
    public void addAttribute(Collection<Attribute> attributes) {
        for (Attribute attribute : attributes) {
            addAttribute(attribute);
        }
    }

    @Override
    public boolean updateAttribute(Attribute attribute) {
        Attribute current = getOrCreateAttribute(attribute.getType());
        if (current.getValue() == attribute.getValue()) {
            return false;
        }

        current.updateValue(attribute.getValue());
        return true;
    }

    public boolean containsAttribute(AttributeType type) {
        return attributeMap.containsKey(type);
    }
}
