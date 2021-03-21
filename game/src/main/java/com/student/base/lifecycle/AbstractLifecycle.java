package com.student.base.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

/**
 * @author : luoyong
 * @date : 2021-03-21 13:28
 **/
public abstract class AbstractLifecycle implements SmartLifecycle, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private boolean running = false;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void start() {
        running = true;
        onStop();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected abstract void onStart();

    protected abstract void onStop();

    @Override
    public int getPhase() {
        return LifecycleOrder.NORMAL.getOrder();
    }
}
