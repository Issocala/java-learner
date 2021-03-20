package com.student.attribute.altermethod.impl;

import com.student.attribute.altermethod.AttributeAlterMethod;
import com.student.attribute.model.Attribute;

import static com.student.attribute.type.AttributeType.ATTRIBUTE_RATIO;

public enum DefaultAlterMethod implements AttributeAlterMethod {
    /**
     * 累加
     */
    ADD() {
        @Override
        public void alter(Attribute attribute, long value) {
            attribute.updateValue(attribute.getValue() + value);
        }
    },
    /**
     * 递减累加
     */
    DECREASE() {
        @Override
        public void alter(Attribute attribute, long value) {
            attribute.updateValue((long) (1 - (1 - attribute.getValue() / ATTRIBUTE_RATIO) * (1 - value / ATTRIBUTE_RATIO)));
        }
    },

    INCREASE() {
        @Override
        public void alter(Attribute attribute, long value) {
            attribute.updateValue((long) ((1 + attribute.getValue() / ATTRIBUTE_RATIO) * (1 + value / ATTRIBUTE_RATIO) - 1));
        }
    },
    ;

}
