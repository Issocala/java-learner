package com.student.attribute.group;

import com.student.attribute.attributecomputer.AttributeComputer;

public interface AttributeTypeGroup {

    AttributeComputer generateComputer(Object[] params);

    byte moduleRelative();
}
