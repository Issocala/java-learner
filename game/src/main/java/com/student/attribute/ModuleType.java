package com.student.attribute;

import com.student.attribute.modulecomputer.ModuleComputer;
import com.student.listener.PlayerActor;

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

    default <E extends PlayerActor> ModuleComputer<E> getModuleCompute() {
        return null;
    }

    default <E extends PlayerActor> SkillModuleComputer<E> getSkillModuleComputer() {
        return null;
    }
}
