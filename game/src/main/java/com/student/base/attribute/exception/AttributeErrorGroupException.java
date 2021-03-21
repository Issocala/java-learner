package com.student.base.attribute.exception;

import com.student.base.attribute.group.AttributeTypeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * /**
 * 分组异常
 *
 * @author : luoyong
 * @date : 2021-03-20 20:02
 **/
public class AttributeErrorGroupException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(AttributeErrorGroupException.class);

    public AttributeErrorGroupException(AttributeTypeGroup group, Object... params) {
        logger.error("属性类型分组：{}，无法创建计算器，参数不足：{}", group, params);
    }

}
