package com.student.base.module;

import com.student.base.attribute.SkillModuleComputer;
import com.student.base.attribute.modulecomputer.ModuleComputer;
import com.student.base.model.GameObject;

/**
 * @author : luoyong
 * @date : 2021-03-20 19:01
 **/
public interface ModuleType {
    String getKey();

    ModuleType getParent();

    default ModuleType[] getRelativeModules() {
        return null;
    }

    default <E extends GameObject> ModuleComputer<E> getModuleCompute() {
        return null;
    }

    default <E extends GameObject> SkillModuleComputer<E> getSkillModuleComputer() {
        return null;
    }
}
