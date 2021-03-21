package com.student.base.attribute.modulecomputer;

import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.type.AttributeType;
import com.student.base.model.GameObject;

import java.util.Map;
import java.util.Set;

/**
 * 模块计算器
 *
 * @author : luoyong
 * @date : 2021-03-20 19:04
 **/
public interface ModuleComputer<E extends GameObject> {
    /**
     * 计算模块属性
     *
     * @param owner
     * @param attributeContainer
     * @param allChildData
     * @param changeTypes
     * @param changeModules
     */
    void compute(E owner, AttributeContainer attributeContainer, Map<ModuleType, AttributeContainer> allChildData,
                 Set<AttributeType> changeTypes, Set<ModuleType> changeModules);
}
