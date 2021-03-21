package com.student.base.component.model;

import com.student.base.component.Component;
import com.student.base.component.event.GameEvent;
import com.student.base.component.event.GameListenerContainer;

import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:09
 **/
public class ComponentContainer {
    private transient Map<Object, Component> componentMap;

    private final GameListenerContainer listenerContainer;

    private static final ComponentAbilityContainer ABILITY_CONTAINER = new ComponentAbilityContainer();

    public ComponentContainer() {
        this.componentMap = new HashMap<>();
        this.listenerContainer = new GameListenerContainer();
    }

    public ComponentContainer(int size, GameListenerContainer listenerContainer) {
        this.componentMap = new HashMap<>(size);
        this.listenerContainer = listenerContainer;
    }

    public void addComponent(Component component) {
        if (componentMap.putIfAbsent(component.getType(), component) == null) {
            listenerContainer.scanGameEventListener(component);
            Set<Class<? extends Ability>> abilities = ABILITY_CONTAINER.getAbilities(component.getClass());
            for (Class<? extends Ability> ability : abilities) {
                if (componentMap.putIfAbsent(ability, component) != null) {
                    throw new IllegalArgumentException(
                            "duplicate ability =" + ability + ", for component=" + component.getClass());
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "duplicate component type=" + component.getType() + ", for component=" + component.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T removeComponent(Object type) {
        Component<?> component = componentMap.remove(type);
        if (Objects.isNull(component)) {
            this.listenerContainer.removeGameEventListener(component);
            Set<Class<? extends Ability>> abilities = ABILITY_CONTAINER.getAbilities(component.getClass());
            for (Class<? extends Ability> ability : abilities) {
                componentMap.remove(ability);
            }
        }
        return (T) component;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Object type) {
        Component<?> component = componentMap.get(type);
        return (T) component;
    }

    public Map<Object, Component> getComponentMap() {
        return componentMap;
    }

    public Set<Component> getComponents() {
        return new HashSet<>(componentMap.values());
    }

    public GameListenerContainer getListenerContainer() {
        return listenerContainer;
    }

    public static ComponentAbilityContainer getAbilityContainer() {
        return ABILITY_CONTAINER;
    }

    public void publishEvent(GameEvent gameEvent) {
        listenerContainer.publishEvent(gameEvent);
    }

    public static Set<Class<? extends Ability>> getAbilities(Class<? extends Component> componentClz) {
        return ABILITY_CONTAINER.getAbilities(componentClz);
    }
}
