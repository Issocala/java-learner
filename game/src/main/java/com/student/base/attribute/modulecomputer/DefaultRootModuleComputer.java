package com.student.base.attribute.modulecomputer;

import com.student.base.module.ModuleType;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.type.AttributeType;
import com.student.base.model.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:47
 **/
public class DefaultRootModuleComputer<E extends GameObject> extends DefaultAccumulateModuleComputer<E> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRootModuleComputer.class);

    @Override
    public void compute(E owner, AttributeContainer attributeContainer, Map<ModuleType, AttributeContainer> allChildData, Set<AttributeType> changeTypes, Set<ModuleType> changeModules) {
        super.compute(owner, attributeContainer, allChildData, changeTypes, changeModules);
//        AttributeComponent component = owner.get
    }
}
