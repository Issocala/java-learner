package com.student.base.model;

import com.student.base.component.Component;
import com.student.base.component.event.GameEvent;
import com.student.base.component.model.ComponentContainer;
import com.student.base.model.serializer.GameObjectSerializeContainer;

import java.util.Map;

/**
 * @author : luoyong
 * @date : 2021-03-20 14:46
 **/
public class GameObject<K> extends AbstractIdentityObject<K> implements Serializable<GameObjectSerializeContainer> {

    private String creatorName;

    protected ComponentContainer componentContainer;

    public GameObject() {
        this.componentContainer = new ComponentContainer();
    }

    public GameObject(K id) {
        this(id, new ComponentContainer());
    }

    public GameObject(ComponentContainer componentContainer) {
        this.componentContainer = componentContainer;
    }

    public GameObject(K id, ComponentContainer componentContainer) {
        this.id = id;
        this.componentContainer = componentContainer;
    }

    @SuppressWarnings("unchecked")
    public void init() {
        this.componentContainer.getListenerContainer().scanGameEventListener(this);
        for (Component component : this.componentContainer.getComponents()) {
            component.init(this);
        }
    }

    @SuppressWarnings("unchecked")
    public void destroy() {
        this.componentContainer.getListenerContainer().scanGameEventListener(this);
        for (Component component : this.componentContainer.getComponents()) {
            component.destroy();
        }
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Object type) {
        return componentContainer.getComponent(type);
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }

    public void addComponent(Component<?> component) {
        componentContainer.addComponent(component);
    }

    public void publishEvent(GameEvent gameEvent) {
        componentContainer.publishEvent(gameEvent);
    }


    @Override
    public GameObjectSerializeContainer serialize() {
        GameObjectSerializeContainer serializeContainer = new GameObjectSerializeContainer();
        serializeContainer.setId(getId());
        serializeContainer.setCreatorName(getCreatorName());
        Map<Object, Component> componentMap = getComponentContainer().getComponentMap();
        for (Component component : componentMap.values()) {
            if (component instanceof Serializable) {
                Object data = ((Serializable) component).serialize();
                serializeContainer.getComponentTypeToData().put(component.getType(), data);
            }
        }
        return serializeContainer;
    }

    @Override
    public void deserialize(GameObjectSerializeContainer data) {
        setId((K) data.getId());
        Map<Object, Component> componentMap = getComponentContainer().getComponentMap();
        for (Map.Entry<Object, Object> entry : data.getComponentTypeToData().entrySet()) {
            Object componentType = entry.getKey();
            Component component = componentMap.get(componentMap);
            if (component instanceof Serializable) {
                ((Serializable) component).deserialize(entry.getValue());
            }
        }
    }
}
