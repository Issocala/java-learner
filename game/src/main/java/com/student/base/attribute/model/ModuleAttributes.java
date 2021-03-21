package com.student.base.attribute.model;

import com.student.base.module.ModuleType;

import java.util.Collection;

/*
 * @author : luoyong
 * @date : 2021-03-20 23:45
 **/
public class ModuleAttributes {

    /**
     * 模块类型
     */
    private ModuleType moduleType;

    /**
     * 属性集合
     */
    private Collection<Attribute> attributes;

    public static ModuleAttributes valueOf(ModuleType moduleType, Collection<Attribute> attributes) {
        ModuleAttributes moduleAttributes = new ModuleAttributes();
        moduleAttributes.moduleType = moduleType;
        moduleAttributes.attributes = attributes;
        return moduleAttributes;
    }
}
