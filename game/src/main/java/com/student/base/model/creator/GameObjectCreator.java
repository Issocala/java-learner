package com.student.base.model.creator;

import com.student.base.component.Component;
import com.student.base.model.GameObject;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.Collection;
import java.util.Set;

public interface GameObjectCreator<T extends GameObject> {
    /**
     * creator的名字，唯一标识，默认是class的短名称
     *
     * @return
     */
    default String getCreatorName() {
        return this.getClass().getSimpleName();
    }

    /**
     * GameObject所以的组件
     *
     * @return
     */
    Set<Class<? extends Component<?>>> getComponentClasses();

    /**
     * 创建的GameObject class
     *
     * @return
     */
    Class<T> getObjectClass();

    /**
     * 默认创建方法
     *
     * @return
     */
    default T create() {
        return create(null);
    }

    default T create(GameObjectInitializer initializer) {
        T obj = ClassUtils.newInstance(getObjectClass());
        obj.setCreatorName(getCreatorName());
        Collection<Class<? extends Component<?>>> componentClasses = getComponentClasses();
        for (Class<? extends Component<?>> componentClz : componentClasses) {
            Component<?> component = ClassUtils.newInstance(componentClz);
            obj.addComponent(component);
        }
        if (initializer != null) {
            initializer.init(obj);
        }
        obj.init();
        return obj;
    }

}
