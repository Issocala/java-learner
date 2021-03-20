package com.student.attribute.container;

import com.student.attribute.ModuleType;
import com.student.attribute.model.Attribute;
import com.student.attribute.type.AttributeType;
import sun.security.pkcs11.Secmod;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
public interface AttributeContainer {
    /**
     * 获取属性类型
     *
     * @param type 类型
     * @return 值
     */
    long getAttributeValue(AttributeType type);

    /**
     * 获取比例值
     *
     * @param type 类型
     * @return 比例值
     */
    double getRatioValue(AttributeType type);

    /**
     * 获取属性对象
     *
     * @param type 类型
     * @return 属性对象
     */
    Attribute getAttribute(AttributeType type);

    /**
     * 获取或创建一个属性对象
     *
     * @param type 类型
     * @return 属性对象
     */
    Attribute getOrCreateAttribute(AttributeType type);

    /**
     * 获取计算后的最终属性
     *
     * @return 属性对象列表
     */
    Collection<Attribute> getAllAttribute();

    /**
     * 获取所有类型
     *
     * @return
     */
    Collection<AttributeType> getAllType();

    /**
     * 与其他属性容器中的属性累加
     *
     * @param containers
     * @param changeModules
     * @return
     */
    Collection<AttributeType> accumulate(Collection<AttributeContainer> containers, Set<ModuleType> changeModules);

    /**
     * 清理所有属性对象
     */
    void clear();

    /**
     * 将所有属性对象的值赋值为0
     */
    void resetValue();

    /**
     * 叠加属性
     *
     * @param attribute
     */
    void addAttribute(Attribute attribute);

    /**
     * 叠加属性
     *
     * @param type
     * @param value
     */
    void addAttribute(AttributeType type, long value);

    /**
     * 叠加属性
     *
     * @param attributes
     */
    void addAttribute(Collection<Attribute> attributes);

    /**
     * 更新属性
     *
     * @param attribute
     * @return true:更新成功 false:值相等
     */
    boolean updateAttribute(Attribute attribute);
}
