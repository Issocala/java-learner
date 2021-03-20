package com.student.attribute.attributecomputer;

import com.student.attribute.container.AttributeContainer;
import com.student.attribute.model.Attribute;
import com.student.attribute.type.AttributeType;
import com.student.listener.PlayerActor;

import java.util.Collection;

/**
 * 属性计算器，用于计算某类型属性的实际值
 */
public interface AttributeComputer {
    /**
     * 计算属性
     *
     * @param actor     属性归属者
     * @param type      属性类型
     * @param container 累加的属性容器
     * @return
     */
    Collection<Attribute> compute(PlayerActor actor, AttributeType type, AttributeContainer container);
}
