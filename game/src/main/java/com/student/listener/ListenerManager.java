package com.student.listener;

import com.student.base.model.GameObject;
import com.student.listener.annotation.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-20 13:09
 **/
@Component
public class ListenerManager {
    private static final Logger log = LoggerFactory.getLogger(ListenerManager.class);

    @Autowired
    private ApplicationContext context;

    private final static Map<Class<?>, List<?>> listeners = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotationMap = context.getBeansWithAnnotation(Listener.class);
        //获取所有Listener类型
        Set<Class<?>> listenerTypes = new HashSet<>();
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            Class<?>[] interfaces = entry.getValue().getClass().getInterfaces();
            for (Class<?> inter : interfaces) {
                if (inter.isAnnotationPresent(Listener.class)) {
                    listenerTypes.add(inter);
                }
            }
        }

        //处理所有的listener实例
        for (Class<?> listenerType : listenerTypes) {
            Map<String, ?> beansOfType = context.getBeansOfType(listenerType);
            List list = listeners.get(listenerType);
            if (Objects.isNull(list)) {
                list = new ArrayList();
                listeners.put(listenerType, list);
            }
            for (Map.Entry<String, ?> entry : beansOfType.entrySet()) {
                list.add(entry.getValue());
            }

            list.sort((o1, o2) -> {
                Listener en1 = findTopestListenerAnnotation(o1);
                Listener en2 = findTopestListenerAnnotation(o2);
                int order1 = en1 == null ? Listener.NORM_PRIORITY : en1.order();
                int order2 = en2 == null ? Listener.NORM_PRIORITY : en2.order();
                return order2 - order1;
            });
        }
    }

    private Listener findTopestListenerAnnotation(Object o) {
        List<Class<?>> classList = new ArrayList<>();
        Class<?> clazz = o.getClass();
        classList.add(clazz);
        classList.addAll(Arrays.asList(o.getClass().getInterfaces()));
        while (true) {
            if (clazz.getSuperclass() != Object.class) {
                clazz = clazz.getSuperclass();
                classList.add(clazz);
                classList.addAll(Arrays.asList(clazz.getInterfaces()));
                continue;
            }
            break;
        }
        for (Class<?> cz : classList) {
            if (cz.isAnnotationPresent(Listener.class)) {
                return cz.getAnnotation(Listener.class);
            }
        }
        return null;
    }

    public <T> List<T> getListeners(Class<T> clazz) {
        List<T> list = (List<T>) listeners.get(clazz);
        if (Objects.isNull(list)) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 触发玩家等级变化
     *
     * @param player   玩家上下文
     * @param oldLevel 玩家旧等级
     * @param newLevel 玩家新等级
     */
    public void firePlayerLevelChangedLister(GameObject player, int oldLevel, int newLevel) {
        Collection<PlayerLevelChangedListener> listeners = getListeners(PlayerLevelChangedListener.class);
        for (PlayerLevelChangedListener levelChangedListener : listeners) {
            try {
                levelChangedListener.onLevelChanged(player, oldLevel, newLevel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
