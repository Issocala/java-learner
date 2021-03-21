package com.student.base.component.event;

import com.student.base.component.event.annotation.GameEventListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : luoyong
 * @date : 2021-03-21 11:23
 **/
public class GameListenerContainer {

    private final Map<Class<? extends GameEvent>, SortedSet<GameListener>> listenerMap = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Map<Method, GameEventListener>> listenerMethods = new ConcurrentHashMap<>();

    public void addGameListener(GameListener listener) {
        Class<? extends GameEvent> eventType = listener.getEventType();
        Set<GameListener> gameListeners = listenerMap.computeIfAbsent(eventType, k -> new TreeSet<>());
        gameListeners.add(listener);
    }

    public void scanGameEventListener(Object listenerOwner) {
        Map<Method, GameEventListener> annotatedMethods = getAnnotatedMethods(listenerOwner);
        for (Method method : annotatedMethods.keySet()) {
            GameListenerMethodAdapter listener = new GameListenerMethodAdapter(listenerOwner, method);
            addGameListener(listener);
        }
    }

    private Map<Method, GameEventListener> getAnnotatedMethods(Object listenerOwner) {
        Map<Method, GameEventListener> annotatedMethods = listenerMethods.get(listenerOwner.getClass());
        if (Objects.isNull(annotatedMethods)) {
            annotatedMethods = MethodIntrospector.selectMethods(listenerOwner.getClass(), (MethodIntrospector.MetadataLookup<GameEventListener>) method -> AnnotatedElementUtils.findMergedAnnotation(method, GameEventListener.class));
            listenerMethods.put(listenerOwner.getClass(), annotatedMethods);
        }
        return annotatedMethods;
    }

    public void publishEvent(GameEvent gameEvent) {
        Set<GameListener> gameListeners = listenerMap.get(gameEvent.getClass());
        if (Objects.isNull(gameListeners)) {
            gameListeners.forEach(listener -> listener.onGameEvent(gameEvent));
        }
    }

    public void removeGameListener(GameListener listener) {
        Class<? extends GameEvent> eventType = listener.getEventType();
        Set<GameListener> gameListeners = this.listenerMap.get(eventType);
        if (gameListeners != null && gameListeners.size() > 0) {
            gameListeners.remove(listener);
        }
    }

    public void removeGameEventListener(Object litenerOwer) {
        Map<Method, GameEventListener> annotatedMethods = getAnnotatedMethods(litenerOwer);
        if (Objects.isNull(annotatedMethods)) {
            return;
        }
        for (Method method : annotatedMethods.keySet()) {
            GameListenerMethodAdapter listener = new GameListenerMethodAdapter(litenerOwer, method);
            this.removeGameListener(listener);
        }
    }
}
