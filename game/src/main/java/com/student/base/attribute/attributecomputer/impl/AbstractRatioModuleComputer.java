package com.student.base.attribute.attributecomputer.impl;

import com.student.base.attribute.component.AttributeComponent;
import com.student.base.module.ModuleType;
import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.model.GameObject;
import org.springframework.util.CollectionUtils;

/**
 * @author : luoyong
 * @date : 2021-03-20 20:13
 **/
public abstract class AbstractRatioModuleComputer implements AttributeComputer {
    protected final ModuleType moduleType;

    public AbstractRatioModuleComputer(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public AttributeContainer getContainer(GameObject owner) {
        AttributeComponent component = (AttributeComponent) owner.getComponent(AttributeComponent.class);
        if (component == null) {
            return null;
        }
        AttributeContainer effectContainer = component.getContainer(moduleType);
        if (effectContainer == null || CollectionUtils.isEmpty(effectContainer.getAllAttribute())) {
            return null;
        }
        return effectContainer;
    }
}
