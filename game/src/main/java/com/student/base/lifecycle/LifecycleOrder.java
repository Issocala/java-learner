package com.student.base.lifecycle;

public enum LifecycleOrder {
    /**
     * 最高优先级
     */
    HIGHEST,
    /**
     * 第二高优先级
     */
    HIGHEST_SECOND,
    /**
     * 第三高优先级
     */
    HIGHEST_THIRD,
    /**
     * 正常优先级
     */
    NORMAL,
    LOWEST_THIRD,
    LOWEST_SECOND,
    /**
     * 最低优先级
     */
    LOWEST;

    public int getOrder() {
        return this.ordinal();
    }

}
