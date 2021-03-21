package com.student.base.struct;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface TreeChildOrder {
    <K, V> Map<K, V> createChildMap();

    <K, V> Map<K, V> createChildMap(int expectedSize);

    static enum SimpleTreeChildOrder implements TreeChildOrder {
        /**
         * 无序
         */
        UNORDERED {
            @Override
            public <K, V> Map<K, V> createChildMap() {
                return new HashMap<>();
            }

            @Override
            public <K, V> Map<K, V> createChildMap(int expectedSize) {
                return new HashMap<>(expectedSize);
            }
        },
        /**
         * 按照插入顺序
         */
        INSERTION {
            @Override
            public <K, V> Map<K, V> createChildMap() {
                return new LinkedHashMap<>();
            }

            @Override
            public <K, V> Map<K, V> createChildMap(int expectedSize) {
                return new LinkedHashMap<>();
            }
        };

        @Override
        public <K, V> Map<K, V> createChildMap() {
            return null;
        }

        @Override
        public <K, V> Map<K, V> createChildMap(int expectedSize) {
            return null;
        }
    }

}
