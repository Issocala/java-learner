package com.student.base.attribute.exception;

import com.student.base.attribute.type.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : luoyong
 * @date : 2021-03-20 23:41
 **/
public class AttributeErrorTypeException {
    private static final Logger logger = LoggerFactory.getLogger(AttributeErrorTypeException.class);

    public AttributeErrorTypeException(AttributeType type) {
        logger.error("属性类型错误：{}", type);
    }
}
