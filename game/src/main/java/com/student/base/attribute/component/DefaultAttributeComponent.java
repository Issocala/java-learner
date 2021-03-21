package com.student.base.attribute.component;

import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.model.ModuleAttributes;
import com.student.base.attribute.modulecomputer.DefaultAccumulateModuleComputer;
import com.student.base.attribute.modulecomputer.ModuleComputer;
import com.student.base.attribute.type.AttributeType;
import com.student.base.component.AbstractComponent;
import com.student.base.model.GameObject;
import com.student.base.model.Serializable;
import com.student.base.module.ModuleType;
import sun.reflect.generics.tree.Tree;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author : luoyong
 * @date : 2021-03-21 14:13
 **/
public class DefaultAttributeComponent<E extends GameObject> extends AbstractComponent<E>
        implements AttributeComponent<E>, AttributeContainer, Serializable<List<ModuleAttributes>> {

    /**
     * 默认属性计算器
     */
    private transient final ModuleComputer<E> defaultModuleComputer = new DefaultAccumulateModuleComputer<>();

//    private Tree<ModuleType, AttributeContainer> attributeTree = new Tree<>{};

    @Override
    public long getAttributeValue(AttributeType type) {
        return 0;
    }

    @Override
    public double getRatioValue(AttributeType type) {
        return 0;
    }

    @Override
    public Attribute getAttribute(AttributeType type) {
        return null;
    }

    @Override
    public Attribute getOrCreateAttribute(AttributeType type) {
        return null;
    }

    @Override
    public Collection<Attribute> getAllAttribute() {
        return null;
    }

    @Override
    public Collection<AttributeType> getAllType() {
        return null;
    }

    @Override
    public Collection<AttributeType> accumulate(Collection<AttributeContainer> containers, Set<ModuleType> changeModules) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void resetValue() {

    }

    @Override
    public void addAttribute(Attribute attribute) {

    }

    @Override
    public void addAttribute(AttributeType type, long value) {

    }

    @Override
    public void addAttribute(Collection<Attribute> attributes) {

    }

    @Override
    public boolean updateAttribute(Attribute attribute) {
        return false;
    }

    @Override
    public List<ModuleAttributes> serialize() {
        return null;
    }

    @Override
    public void deserialize(List<ModuleAttributes> data) {

    }
}
