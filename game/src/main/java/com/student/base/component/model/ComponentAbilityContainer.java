package com.student.base.component.model;

import com.student.base.component.Component;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:51
 **/
public class ComponentAbilityContainer {

    private Map<Class<? extends Component>, Set<Class<? extends Ability>>> componentClzToAbilities = new ConcurrentHashMap<>();

    public Set<Class<? extends Ability>> getAbilities(Class<? extends Component> componentClz) {
        Set<Class<? extends Ability>> abilities = componentClzToAbilities.get(componentClz);
        if (Objects.isNull(abilities)) {
            abilities = componentClzToAbilities.computeIfAbsent(componentClz, clz -> {
                Set<Class<? extends Ability>> abilityClzs = new HashSet<>();
                List<Class<?>> allInterfaces = Arrays.asList(ClassUtils.getAllInterfaces(clz));
                for (Class<?> clazz : allInterfaces) {
                    if (clazz != Ability.class && Ability.class.isAssignableFrom(clazz)) {
                        abilityClzs.add((Class<? extends Ability>) clazz);
                    }
                }
                return abilityClzs;
            });
        }
        return abilities;
    }
}
