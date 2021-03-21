package com.student.listener;

import com.student.base.model.GameObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 玩家等级变化触发，和监听demo
 *
 * @author : luoyong
 * @date : 2021-03-20 14:54
 **/
@Component
@ComponentScan(value = "com.student")
public class PlayerLevelUpDemo implements PlayerLevelChangedListener {

    @Autowired
    private ListenerManager listenerManager;

    @Override
    public void onLevelChanged(GameObject player, int oldLevel, int newLevel) {
        System.out.println("玩家等级变化了--" + oldLevel + "----" + newLevel);
    }

    /**
     * 升级操作
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PlayerLevelUpDemo.class);
        applicationContext.refresh();
        PlayerLevelUpDemo playerLevelUpDemo = applicationContext.getBean(PlayerLevelUpDemo.class);

        playerLevelUpDemo.up();


    }

    public void up() {
        //触发玩家等级变化。。。1 -> 2
        listenerManager.firePlayerLevelChangedLister(new GameObject(), 1, 2);
    }
}
