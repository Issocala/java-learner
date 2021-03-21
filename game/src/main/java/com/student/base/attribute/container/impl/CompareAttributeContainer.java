package com.student.base.attribute.container.impl;

import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.type.AttributeType;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 属性比对容器，一般用于根节点进行属性对比收集changeTypes 注意：不要进行序列化，序列化同一使用SimpleAttributeContainer
 *
 * @author : luoyong
 * @date : 2021-03-20 23:20
 **/
public class CompareAttributeContainer extends SimpleAttributeContainer {

    protected final SimpleAttributeContainer accumulateContainer = new SimpleAttributeContainer();
    protected final SimpleAttributeContainer accumulateHistoryContainer = new SimpleAttributeContainer();
    protected final SimpleAttributeContainer historyContainer = new SimpleAttributeContainer();

    public CompareAttributeContainer() {
        super();
    }

    public CompareAttributeContainer(Map<AttributeType, Attribute> attributeMap) {
        super(attributeMap);
    }

    @Override
    public Collection<AttributeType> accumulate(Collection<AttributeContainer> containers, Set<ModuleType> changeModules) {
        return super.accumulate(containers, changeModules);
    }

    private void changeRelativeModule(Set<ModuleType> moduleTypes, Collection<AttributeType> changedTypes) {
        for (ModuleType moduleType : moduleTypes) {
            Collection<? extends AttributeType> attributeTypes = AttributeType.gainModuleAttributeTypes(moduleType);
            for (AttributeType attributeType : attributeTypes) {
                if (containsAttribute(attributeType) || accumulateContainer.containsAttribute(attributeType)) {
                    addChangeType(changedTypes, attributeType);
                }
            }
        }
    }


    private void addChangeType(Collection<AttributeType> changedTypes, AttributeType type) {
        if (changedTypes.contains(type)) {
            return;
        }
        changedTypes.add(type);
        Attribute changeAttribute = accumulateContainer.getAttribute(type);
        if (changeAttribute != null) {
            updateAttribute(changeAttribute);
        }

        Collection<? extends AttributeType> relativeTypes = AttributeType.gainGenerateTypes(type);
        if (relativeTypes != null && relativeTypes.size() > 0) {
            for (AttributeType t : relativeTypes) {
                if (checkHave(t)) {
                    addChangeType(changedTypes, t);
                }
            }
        }

        Collection<? extends AttributeType> sourceTypes = AttributeType.gainSourceTypes(type);
        if (sourceTypes != null && sourceTypes.size() > 0) {
            for (AttributeType t : sourceTypes) {
                if (checkHave(t)) {
                    addChangeType(changedTypes, t);
                }
            }
        }
    }

    /**
     * 检测旧属性集合或新属性集合是否有该属性
     *
     * @param t
     * @return
     */
    private boolean checkHave(AttributeType t) {
        Attribute a1 = accumulateContainer.getAttribute(t);
        Attribute a2 = attributeMap.get(t);
        return (a1 != null && a1.getValue() != 0) || (a2 != null && a2.getValue() != 0);
    }

    /**
     * 添加当前留下的类型，以及关联的类型
     */
    private void addCurrentTypes(Collection<AttributeType> attributeTypes,
                                 Collection<AttributeType> changedTypes, AttributeType type) {

        if (changedTypes.contains(type)) {
            return;
        }
        attributeTypes.add(type);
        Collection<? extends AttributeType> relativeTypes = AttributeType.gainGenerateTypes(type);
        if (relativeTypes == null) {
            return;
        }

        for (AttributeType relativeType : relativeTypes) {
            addCurrentTypes(attributeTypes, changedTypes, relativeType);
        }
    }

    public SimpleAttributeContainer getAccumulateContainer() {
        return accumulateContainer;
    }

    public SimpleAttributeContainer getAccumulateHistoryContainer() {
        return accumulateHistoryContainer;
    }

    public SimpleAttributeContainer getHistoryContainer() {
        return historyContainer;
    }
}
