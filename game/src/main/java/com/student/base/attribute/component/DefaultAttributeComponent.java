package com.student.base.attribute.component;

import com.student.base.attribute.container.AttributeContainer;
import com.student.base.attribute.container.impl.CompareAttributeContainer;
import com.student.base.attribute.container.impl.SimpleAttributeContainer;
import com.student.base.attribute.ge.AttributeRefreshEvent;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.model.ModuleAttributes;
import com.student.base.attribute.modulecomputer.impl.DefaultAccumulateModuleComputer;
import com.student.base.attribute.modulecomputer.ModuleComputer;
import com.student.base.attribute.type.AttributeType;
import com.student.base.component.AbstractComponent;
import com.student.base.model.GameObject;
import com.student.base.model.Serializable;
import com.student.base.module.ModuleType;
import com.student.base.struct.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-21 14:13
 **/
public class DefaultAttributeComponent<E extends GameObject> extends AbstractComponent<E>
        implements AttributeComponent<E>, AttributeContainer, Serializable<List<ModuleAttributes>> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAttributeComponent.class);

    /**
     * 默认属性计算器
     */
    private transient final ModuleComputer<E> defaultModuleComputer = new DefaultAccumulateModuleComputer<>();

    /**
     * 属性树信息
     */
    private Tree<ModuleType, AttributeContainer> attributeTree = new Tree<>();

    /**
     * 模块特有计算器
     */
    private Map<ModuleType, ModuleComputer<E>> moduleComputerMap = new HashMap<>(1);

    /**
     * 属性变更缓存
     */
    private Map<Integer, Set<ModuleType>> buffer = new TreeMap<>(Comparator.reverseOrder());

    /**
     * 模块变更缓存
     */
    private Set<ModuleType> changeModules = new HashSet<>();

    /**
     * 关联的其他属性组件的模块
     */
    private Map<AttributeComponent, ModuleType> links = new HashMap<>(2);

    public DefaultAttributeComponent(E owner, ModuleType rootType) {
        super(owner);
        attributeTree.setRoot(rootType, new CompareAttributeContainer());
    }

    @Override
    public long getAttributeValue(AttributeType type) {
        return attributeTree.getRootData().getAttributeValue(type);
    }

    @Override
    public double getRatioValue(AttributeType type) {
        return attributeTree.getRootData().getRatioValue(type);
    }

    @Override
    public Attribute getAttribute(AttributeType type) {
        return attributeTree.getRootData().getAttribute(type);
    }

    /**
     * 返回累加后的基础属性
     *
     * @return
     */
    public Collection<Attribute> getBasicAttributes() {
        CompareAttributeContainer attributeContainer = (CompareAttributeContainer) attributeTree.getRootData();
        SimpleAttributeContainer accumulateContainer = attributeContainer.getAccumulateContainer();
        return accumulateContainer.getAllAttribute();
    }

    /**
     * 返回属性变更前数值
     *
     * @return
     */
    public Collection<Attribute> getHistAttributes() {
        CompareAttributeContainer attributeContainer = (CompareAttributeContainer) attributeTree.getRootData();
        SimpleAttributeContainer accumulateContainer = attributeContainer.getHistoryContainer();
        return accumulateContainer.getAllAttribute();
    }

    @Override
    public Attribute getOrCreateAttribute(AttributeType type) {
        return attributeTree.getRootData().getOrCreateAttribute(type);
    }

    /**
     * 完成计算后的最终属性
     *
     * @return
     */
    @Override
    public Collection<Attribute> getAllAttribute() {
        return attributeTree.getRootData().getAllAttribute();
    }

    @Override
    public Collection<AttributeType> getAllType() {
        return attributeTree.getRootData().getAllType();
    }

    @Override
    public Collection<AttributeType> accumulate(Collection<AttributeContainer> containers, Set<ModuleType> changeModules) {
        return attributeTree.getRootData().accumulate(containers, changeModules);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resetValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAttribute(Attribute attribute) {
        attributeTree.getRootData().addAttribute(attribute);
    }

    @Override
    public void addAttribute(AttributeType type, long value) {
        attributeTree.getRootData().addAttribute(type, value);
    }

    @Override
    public void addAttribute(Collection<Attribute> attributes) {
        attributeTree.getRootData().addAttribute(attributes);
    }

    @Override
    public boolean updateAttribute(Attribute attribute) {
        return attributeTree.getRootData().updateAttribute(attribute);
    }

    @Override
    public AttributeContainer getContainer(ModuleType type) {
        return attributeTree.getData(type);
    }

    @Override
    public void injectModuleComputer(ModuleType moduleType, ModuleComputer<E> computer) {
        moduleComputerMap.put(moduleType, computer);
    }

    @Override
    public void replace(ModuleType target, AttributeContainer container, boolean refresh) {
        // 替换并从target节点刷新
        ModuleType parent = target.getParent();
        if (!attributeTree.containKey(parent)) {
            fixParent(parent);
        }

        if (container instanceof AttributeComponent) {
            ((AttributeComponent) container).link(this, target);
        }

        attributeTree.replace(parent, target, container);
        addChangeModule(target);
        if (refresh) {
            refreshNow(parent);
        } else {
            addBuffer(parent);
        }
    }

    private void refreshNow(ModuleType parent) {
        // 如果缓存池有内容，则将缓存内容一起刷新。否则只刷单个节点
        if (buffer.size() > 0) {
            addBuffer(parent);
            flush();
        } else {
            refresh(parent);
        }
    }

    private void addBuffer(ModuleType parent) {
        int level = attributeTree.getLevel(parent);
        Set<ModuleType> moduleTypeSet = buffer.computeIfAbsent(level, k -> new HashSet<>());
        moduleTypeSet.add(parent);
        if (parent.getParent() != null) {
            addBuffer(parent.getParent());
        }
    }

    private void addChangeModule(ModuleType target) {
        if (changeModules.contains(target)) {
            return;
        }
        changeModules.add(target);
        if (target.getRelativeModules() != null && target.getRelativeModules().length > 0) {
            for (ModuleType relative : target.getRelativeModules()) {
                if (attributeTree.containKey(relative)) {
                    addChangeModule(relative);
                }
            }
        }
    }

    @Override
    public void refresh(ModuleType type) {
        // 最终刷新计算，清空缓冲区
        buffer.clear();
        Tree.ChildToRootIterator<ModuleType, AttributeContainer> iterator = attributeTree.iteratorFromChildToRoot(type);
        Set<AttributeType> changeTypes = new HashSet<>();
        while (iterator.hasNext()) {
            Tree.TreeNode<ModuleType, AttributeContainer> node = iterator.next();
            if (logger.isDebugEnabled()) {
                logger.debug("计算节点：{}，有无父节点：{}", node.getKey(), node.getParent() != null);
            }
            addChangeModule(node.getKey());
            ModuleComputer<E> computer = getModuleComputer(node.getKey());
            computer.compute(getOwner(), node.getData(), node.getChildDataMap(), changeTypes, changeModules);
        }
        afterRefresh();
    }

    /**
     * 刷新所有节点后执行的操作
     */
    private void afterRefresh() {
        //清楚缓存的变化模块
        this.changeModules.clear();
        //抛出属性刷新事件
        getOwner().publishEvent(AttributeRefreshEvent.valueOf(getOwner(), this));

        //如果当前组件作为某个节点，被关联到其他组件上，则触发其他组件的刷新
        for (Map.Entry<AttributeComponent, ModuleType> entry : links.entrySet()) {
            entry.getKey().refresh(entry.getValue().getParent());
        }
    }

    @Override
    public void flush() {
        // 刷新缓冲区
        Iterator<Integer> iterator = buffer.keySet().iterator();
        Set<AttributeType> changeTypes = new HashSet<>();
        Integer level = null;
        ModuleType lastModule = null;

        while (iterator.hasNext()) {
            level = iterator.next();
            Set<ModuleType> moduleSet = buffer.get(level);
            if (logger.isDebugEnabled()) {
                logger.debug("刷新level:{}", level);
            }

            for (ModuleType module : moduleSet) {
                lastModule = module;

                addChangeModule(module);
                if (Tree.ROOT_LEVEL.equals(level)) {
                    refresh(lastModule);
                    return;
                } else {
                    Tree.TreeNode<ModuleType, AttributeContainer> node = attributeTree.getNode(module);
                    ModuleComputer<E> computer = getModuleComputer(node.getKey());
                    computer.compute(getOwner(), node.getData(), node.getChildDataMap(), changeTypes, changeModules);
                }
            }
        }
    }

    private ModuleComputer<E> getModuleComputer(ModuleType key) {
        ModuleComputer<E> moduleComputer = moduleComputerMap.get(key);
        if (moduleComputer == null) {
            moduleComputer = key.getModuleComputer();
            if (moduleComputer != null) {
                injectModuleComputer(key, moduleComputer);
                return moduleComputer;
            }
        }
        return moduleComputer != null ? moduleComputer : defaultModuleComputer;
    }

    @Override
    public void link(AttributeComponent component, ModuleType moduleType) {
        if (logger.isDebugEnabled()) {
            logger.debug("属性组件：{}，与属性组件：{}，进行关联，关联模块：{}",
                    this.getOwner().getId(), component.getOwner().getId(), moduleType);
        }
        this.links.put(component, moduleType);
    }

    @Override
    public void unLink(AttributeComponent component, ModuleType moduleType) {
        if (logger.isDebugEnabled()) {
            logger.debug("属性组件：{}，与属性组件：{}，解除关联，关联模块：{}",
                    this.getOwner().getId(), component.getOwner().getId(), moduleType);
        }
        this.links.remove(component);
    }

    @Override
    public Set<ModuleType> getAllModule() {
        return attributeTree.keySet();
    }

    private void fixParent(ModuleType key) {
        ModuleType parentKey = key.getParent();
        if (parentKey == null) {
            throw new RuntimeException("已经追溯到根节点，无法修复，检查是否初始化属性组件");
        }

        if (!attributeTree.containKey(parentKey)) {
            fixParent(parentKey);
        }
        attributeTree.replace(parentKey, key, new SimpleAttributeContainer());
    }

    @Override
    public List<ModuleAttributes> serialize() {
        List<ModuleAttributes> attributes = new ArrayList<>();
        attributeTree.forEach((moduleType, container) -> {
            if (moduleType.getParent() == null) {
                return;
            }
            ModuleAttributes moduleAttributes = ModuleAttributes.valueOf(moduleType, container.getAllAttribute());
            attributes.add(moduleAttributes);
        });

        return attributes;
    }

    @Override
    public void deserialize(List<ModuleAttributes> data) {
        for (ModuleAttributes moduleAttributes : data) {
            SimpleAttributeContainer simpleAttributeContainer = new SimpleAttributeContainer(moduleAttributes.getAttributes());
            replace(moduleAttributes.getModuleType(), simpleAttributeContainer, false);
        }
    }

    /**
     * 获取叶子节点属性
     *
     * @return
     */
    public Map<ModuleType, AttributeContainer> getLeafAttributeMap() {
        Map<ModuleType, AttributeContainer> attributeContainerMap = new HashMap<>();
        Set<ModuleType> moduleTypes = attributeTree.keySet();
        for (ModuleType moduleType : moduleTypes) {
            Tree.TreeNode<ModuleType, AttributeContainer> node = attributeTree.getNode(moduleType);
            if (!node.isLeafNode()) {
                continue;
            }
            attributeContainerMap.put(moduleType, node.getData());
        }
        return attributeContainerMap;
    }

    /**
     * 获取叶子节点属性
     *
     * @return
     */
    public Map<ModuleType, AttributeContainer> getLeafAttributes(ModuleType moduleType) {
        Map<ModuleType, AttributeContainer> attributeContainers = new HashMap<>();
        Tree.TreeNode<ModuleType, AttributeContainer> node = attributeTree.getNode(moduleType);
        List<Tree.TreeNode<ModuleType, AttributeContainer>> allLeafNodes = node.getAllLeafNodes();
        allLeafNodes.forEach(treeNode -> attributeContainers.put(treeNode.getKey(), treeNode.getData()));
        return attributeContainers;
    }
}
