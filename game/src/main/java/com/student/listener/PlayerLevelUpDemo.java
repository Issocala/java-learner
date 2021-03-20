package com.student.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 玩家等级变化触发，和监听demo
 *
 * @author : luoyong
 * @date : 2021-03-20 14:54
 **/
@Component
public class PlayerLevelUpDemo implements PlayerLevelChangedListener {

    @Autowired
    private ListenerManager listenerManager;

    @Override
    public void onLevelChanged(PlayerActor player, int oldLevel, int newLevel) {
        System.out.println("玩家等级变化了--" + oldLevel + "----" + newLevel);
    }

    /**
     * 升级操作
     */
    public void up() {
        //触发玩家等级变化。。。1 -> 2
        listenerManager.firePlayerLevelChangedLister(new PlayerActor(), 1, 2);
    }
}
