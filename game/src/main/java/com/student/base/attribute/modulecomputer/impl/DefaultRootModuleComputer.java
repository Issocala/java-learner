package com.student.base.attribute.modulecomputer.impl;

import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.component.AttributeComponent;
import com.student.base.attribute.model.Attribute;
import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.type.AttributeType;
import com.student.base.model.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:47
 **/
public class DefaultRootModuleComputer<E extends GameObject> extends DefaultAccumulateModuleComputer<E> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRootModuleComputer.class);

    @Override
    public void compute(E owner, AttributeContainer attributeContainer, Map<ModuleType, AttributeContainer> allChildData, Set<AttributeType> changeTypes, Set<ModuleType> changeModules) {
        super.compute(owner, attributeContainer, allChildData, changeTypes, changeModules);
        AttributeComponent component = (AttributeComponent) owner.getComponent(AttributeComponent.class);

        List<AttributeType> changeTypeList = new ArrayList<>(changeTypes);
        changeTypeList.sort(((o1, o2) -> {
            int compare = Integer.compare(AttributeType.orderOf(o1.getAttributeTypeGroup()),
                    AttributeType.orderOf(o2.getAttributeTypeGroup()));
            // 组类型相同，判断派生关系
            if (compare == 0) {
                return AttributeType.checkGenerate(o2, o1);
            }
            return compare;
        }));

        /**
         * 根据类型规定的计算顺序，计算所有变更了的属性，并将结果直接更新到累加容器中。
         */
        Collection<Attribute> computeResult;
        for (AttributeType type : changeTypeList) {
            Attribute attribute = attributeContainer.getAttribute(type);
            if (attribute == null || attribute.getValue() == 0) {
                Attribute oldValue = component.getAttribute(type);
                if (oldValue != null) {
                    //旧值清零
                    oldValue.updateValue(0);
                }
                continue;
            }

            AttributeComputer computer = AttributeType.gainComputer(type);
            if (component == null) {
                continue;
            }

            computeResult = computer.compute(owner, type, attributeContainer);
            if (computeResult != null && computeResult.size() > 0) {
                attributeContainer.addAttribute(computeResult);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("变更属性：{}", attributeContainer);
        }

        // 将所有变更值更新到最终结果里
        for (Attribute attribute : attributeContainer.getAllAttribute()) {
            component.updateAttribute(attribute);
        }
    }
}
