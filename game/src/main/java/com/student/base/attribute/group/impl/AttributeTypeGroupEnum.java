package com.student.base.attribute.group.impl;

import com.student.base.module.ModuleType;
import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.attributecomputer.impl.FixValueAttributeComputer;
import com.student.base.attribute.attributecomputer.impl.RatioAttributeComputer;
import com.student.base.attribute.attributecomputer.impl.RatioModuleAttributesComputer;
import com.student.base.attribute.exception.AttributeErrorGroupException;
import com.student.base.attribute.group.AttributeTypeGroup;
import com.student.base.attribute.group.constant.AttributeGroupConstant;
import com.student.base.attribute.type.AttributeType;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author : luoyong
 * @date : 2021-03-20 19:58
 **/
public enum AttributeTypeGroupEnum implements AttributeTypeGroup {
    /**
     * 值，属性值不具有特殊意义
     */
    Value(),
    /**
     * 比例，属性值被当作比例看待，默认是万分比
     */
    Ratio(),
    /**
     * 固定值，属性值经过计算，会转变为其他值
     */
    FixValue() {
        @Override
        public AttributeComputer generateComputer(Object[] params) {
            Collection<AttributeType> effectTypes = new HashSet<>(params.length);
            for (Object param : params) {
                if (param instanceof AttributeType) {
                    effectTypes.add((AttributeType) param);
                }
            }

            if (effectTypes.size() <= 0) {
                throw new AttributeErrorGroupException(this, params);
            }
            return new FixValueAttributeComputer(effectTypes);
        }
    },
    /**
     * 某属性的比例，该属性的值表示按比例提升某属性， 如：攻击力比例
     */
    RatioAttribute() {
        @Override
        public AttributeComputer generateComputer(Object[] params) {
            Collection<AttributeType> effectTypes = new HashSet<>(params.length);
            for (Object param : params) {
                if (param instanceof AttributeType) {
                    effectTypes.add((AttributeType) param);
                }
            }

            if (effectTypes.size() <= 0) {
                throw new AttributeErrorGroupException(this, params);
            }
            return new RatioAttributeComputer(effectTypes);
        }
    },
    /**
     * 某模块、某属性的比例，该属性的值表示按比例提升某模块的某些属性，如：翅膀的攻击力比例
     */
    RatioModuleAttributes() {
        @Override
        public byte moduleRelative() {
            return AttributeGroupConstant.MODULE_PIECE;
        }

        @Override
        public AttributeComputer generateComputer(Object[] params) {
            ModuleType moduleType = null;
            Collection<AttributeType> effectTypes = new HashSet<>(params.length);
            for (Object param : params) {
                if (param instanceof ModuleType) {
                    moduleType = (ModuleType) param;
                } else if (param instanceof AttributeType) {
                    effectTypes.add((AttributeType) param);
                }
            }

            if (moduleType == null) {
                throw new AttributeErrorGroupException(this, params);
            }

            if (effectTypes.size() <= 0) {
                throw new AttributeErrorGroupException(this, params);
            }
            return new RatioModuleAttributesComputer(moduleType, effectTypes);
        }
    };

    @Override
    public AttributeComputer generateComputer(Object[] params) {
        return null;
    }

    @Override
    public byte moduleRelative() {
        return AttributeGroupConstant.MODULE_NONE;
    }
}
