package com.student.attribute.attributecomputer.impl;

import com.student.attribute.ModuleType;
import com.student.attribute.attributecomputer.AttributeComputer;
import com.student.attribute.container.AttributeContainer;
import com.student.listener.PlayerActor;

/**
 * @author : luoyong
 * @date : 2021-03-20 20:13
 **/
public abstract class AbstractRatioModuleComputer implements AttributeComputer {
    protected final ModuleType moduleType;

    public AbstractRatioModuleComputer(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public AttributeContainer getContainer(PlayerActor owner) {
        return null;
    }
}
