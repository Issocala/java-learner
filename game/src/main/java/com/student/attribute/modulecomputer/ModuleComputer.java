package com.student.attribute.modulecomputer;

import com.student.attribute.ModuleType;
import com.student.attribute.container.AttributeContainer;
import com.student.attribute.type.AttributeType;
import com.student.listener.PlayerActor;

import java.util.Map;
import java.util.Set;

/**
 * 模块计算器
 *
 * @author : luoyong
 * @date : 2021-03-20 19:04
 **/
public interface ModuleComputer<E extends PlayerActor> {
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
