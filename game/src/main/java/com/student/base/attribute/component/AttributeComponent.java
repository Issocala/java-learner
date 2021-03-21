package com.student.base.attribute.component;

import com.student.base.attribute.modulecomputer.ModuleComputer;
import com.student.base.component.model.Ability;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.component.Component;
import com.student.base.model.GameObject;
import com.student.base.module.ModuleType;

import java.util.Set;

/**
 * @author : luoyong
 * @date : 2021-03-20 22:57
 **/
public interface AttributeComponent<E extends GameObject> extends Component<E>, AttributeContainer, Ability {


    /**
     * 获取某模块的属性容器
     *
     * @param type 模块类型
     * @return
     */
    AttributeContainer getContainer(ModuleType type);

    /**
     * 注入模块计算器
     *
     * @param moduleType 模块类型
     * @param computer   模块计算器
     */
    void injectModuleComputer(ModuleType moduleType, ModuleComputer<E> computer);

    /**
     * 更换某模块
     *
     * @param target    模块类型
     * @param container 属性容器
     * @param refresh   是否刷新
     */
    void replace(ModuleType target, AttributeContainer container, boolean refresh);

    /**
     * 从某个节点开始刷新
     *
     * @param type 模块类型
     */
    void refresh(ModuleType type);

    /**
     * 刷新所以缓存的变更操作
     */
    void flush();

    /**
     * 与其他组件的连接
     *
     * @param component  目标组件
     * @param moduleType 链接到的模块类型
     */
    void link(AttributeComponent component, ModuleType moduleType);

    /**
     * 接触链接
     *
     * @param component  目标组件
     * @param moduleType 链接到的模块类型
     */
    void unLink(AttributeComponent component, ModuleType moduleType);

    Set<ModuleType> getAllModule();
}
