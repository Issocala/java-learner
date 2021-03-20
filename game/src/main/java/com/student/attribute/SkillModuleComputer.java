package com.student.attribute;

import com.student.listener.PlayerActor;

import java.util.Collection;

public interface SkillModuleComputer<E extends PlayerActor> {

    void compute(E owner, SkillContainer skillContainer, Collection<SkillContainer> allChildData);
}
