package com.student.base.model.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:03
 **/
public class GameObjectSerializeContainer {

    private String creatorName;

    private Object id;

    private Map<Object, Object> componentTypeToData = new HashMap<>();

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Map<Object, Object> getComponentTypeToData() {
        return componentTypeToData;
    }

    public void setComponentTypeToData(Map<Object, Object> componentTypeToData) {
        this.componentTypeToData = componentTypeToData;
    }
}
