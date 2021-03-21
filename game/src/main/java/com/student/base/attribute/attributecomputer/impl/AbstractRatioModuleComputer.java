package com.student.base.attribute.attributecomputer.impl;

import com.student.base.module.ModuleType;
import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.model.GameObject;

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
        return null;
    }
}
