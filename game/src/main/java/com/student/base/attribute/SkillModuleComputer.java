package com.student.base.attribute;

import com.student.base.model.GameObject;

import java.util.Collection;

public interface SkillModuleComputer<E extends GameObject> {

    void compute(E owner, SkillContainer skillContainer, Collection<SkillContainer> allChildData);
}
