package com.student.base.attribute.group;

import com.student.base.attribute.attributecomputer.AttributeComputer;

public interface AttributeTypeGroup {

    AttributeComputer generateComputer(Object[] params);

    byte moduleRelative();
}
