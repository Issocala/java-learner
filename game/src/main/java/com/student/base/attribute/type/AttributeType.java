package com.student.base.attribute.type;

import com.student.base.module.ModuleType;
import com.student.base.attribute.altermethod.AttributeAlterMethod;
import com.student.base.attribute.altermethod.impl.DefaultAlterMethod;
import com.student.base.attribute.attributecomputer.AttributeComputer;
import com.student.base.attribute.group.AttributeTypeGroup;
import com.student.base.attribute.group.constant.AttributeGroupConstant;
import com.student.base.attribute.group.impl.AttributeTypeGroupEnum;
import com.student.base.attribute.model.Attribute;
import com.student.base.attribute.model.impl.IntegerAttribute;

import java.util.*;

public interface AttributeType {

    /**
     * 比例值的标准值，默认是万分比
     */
    double ATTRIBUTE_RATIO = 10000D;

    /**
     * 获取属性类型会衍生出的类型
     *
     * @param type
     * @return
     */
    static Collection<? extends AttributeType> gainGenerateTypes(AttributeType type) {
        return AttributeTypeManager.GENERATE_TYPES.get(type);
    }

    /**
     * 获取属性类型可能由列表中的属性衍生
     *
     * @param type
     * @return
     */
    static Collection<? extends AttributeType> gainSourceTypes(AttributeType type) {
        return AttributeTypeManager.SOURCE_TYPES.get(type);
    }

    /**
     * 模块改变 可能需要重算的属性类型
     *
     * @param type
     * @return
     */
    static Collection<? extends AttributeType> gainModuleAttributeTypes(ModuleType type) {
        return AttributeTypeManager.MODULE_TYPES.getOrDefault(type, Collections.emptySet());
    }

    /**
     * 注入类型实现
     *
     * @param type
     */
    static void injectType(AttributeType type) {
        AttributeTypeManager.TYPE_MAP.put(type.getKey(), type);
        AttributeTypeGroup group = type.getAttributeTypeGroup();
        if (group.moduleRelative() > AttributeGroupConstant.MODULE_NONE) {
            AttributeTypeManager.collectRelativeModules(type, type.getExtendParams());
        }
        AttributeComputer computer = group.generateComputer(type.getExtendParams());
        if (computer != null) {
            AttributeTypeManager.COMPUTER_MAP.put(type, computer);
            AttributeTypeManager.collectRelativeModules(type, type.getExtendParams());
        }
    }

    /**
     * 批量注入类型实现
     *
     * @param typeArray
     */
    static void injectTypes(AttributeType[] typeArray) {
        for (AttributeType type : typeArray) {
            injectType(type);
        }
    }

    /**
     * 根据关键字获取属性类型
     */
    static AttributeType gainType(String key) {
        return AttributeTypeManager.TYPE_MAP.get(key);
    }

    /**
     * 获取类型对应的属性计算器
     *
     * @param type
     * @return
     */
    static AttributeComputer gainComputer(AttributeType type) {
        return AttributeTypeManager.COMPUTER_MAP.get(type);
    }

    /**
     * 获取属性类型分组的顺序
     *
     * @param group
     * @return
     */
    static int orderOf(AttributeTypeGroup group) {
        return AttributeTypeManager.GROUP_ORDER.indexOf(group);
    }

    /**
     * 对属性类型分组的计算顺序进行排序
     *
     * @param groupOrder
     */
    static void changeOrder(AttributeTypeGroup... groupOrder) {
        AttributeTypeManager.GROUP_ORDER = Arrays.asList(groupOrder);
    }

    static int checkGenerate(AttributeType o1, AttributeType o2) {
        if (gainGenerateTypes(o1) != null && gainGenerateTypes(o1).contains(o2)) {
            return 1;
        } else if (gainGenerateTypes(o2) != null && gainGenerateTypes(o2).contains(o1)) {
            return -1;
        }
        return 0;
    }


    /**
     * 类型的唯一标识
     *
     * @return
     */
    String getKey();

    /**
     * 属性类型分组
     *
     * @return
     */
    AttributeTypeGroup getAttributeTypeGroup();

    /**
     * 类型的扩展参数
     *
     * @return
     */
    default Object[] getExtendParams() {
        return null;
    }

    /**
     * 根据类型生成一个属性对象
     *
     * @param value
     * @return
     */
    default Attribute generateAttribute(long value) {
        return new IntegerAttribute(this, value);
    }

    /**
     * 获取累加方式
     *
     * @return
     */
    default AttributeAlterMethod getAlterMethod() {
        return DefaultAlterMethod.ADD;
    }


    /**
     * 属性类型管理类
     */
    class AttributeTypeManager {
        private static Map<String, AttributeType> TYPE_MAP = new HashMap<>();
        private static Map<AttributeType, AttributeComputer> COMPUTER_MAP = new HashMap<>();
        private static List<AttributeTypeGroup> GROUP_ORDER = new ArrayList<>();
        /**
         * 类型，映射生成属性列表，表达某个类型属性会生成列表中的属性类型。
         */
        private static Map<AttributeType, Set<AttributeType>> GENERATE_TYPES = new HashMap<>();

        /**
         * 类型，映射来源属性列表。表达某个类型属性可能由列表中的属性类型生成。
         */
        private static Map<AttributeType, Set<AttributeType>> SOURCE_TYPES = new HashMap<>();
        /**
         * <模块类型， 关联属性列表> 表达某个模块变化需要重算对应属性列表 例如武器比例、翅膀攻击比例
         */
        private static Map<ModuleType, Set<AttributeType>> MODULE_TYPES = new HashMap<>();

        static {
            changeOrder(AttributeTypeGroupEnum.RatioModuleAttributes,
                    AttributeTypeGroupEnum.RatioAttribute, AttributeTypeGroupEnum.Ratio, AttributeTypeGroupEnum.Value,
                    AttributeTypeGroupEnum.FixValue);
        }

        static void collectRelativeTypes(AttributeType type, Object[] extendParams) {
            for (Object param : extendParams) {
                if (param instanceof AttributeType) {
                    relativeType(type, (AttributeType) param);
                }
            }
        }

        private static void relativeType(AttributeType type1, AttributeType type2) {
            Set<AttributeType> types;
            types = GENERATE_TYPES.computeIfAbsent(type1, k -> new HashSet<>());
            types.add(type2);

            types = SOURCE_TYPES.computeIfAbsent(type2, k -> new HashSet<>());
            types.add(type1);
        }

        static void collectRelativeModules(AttributeType type, Object[] extendParams) {
            for (Object param : extendParams) {
                if (param instanceof ModuleType) {
                    Set<AttributeType> attributeTypes =
                            MODULE_TYPES.computeIfAbsent((ModuleType) param, k -> new HashSet<>());
                    attributeTypes.add(type);
                }
            }
        }
    }

}
