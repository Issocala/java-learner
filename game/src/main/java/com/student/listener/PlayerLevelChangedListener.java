package com.student.listener;

import com.student.listener.annotation.Listener;

/**
 * 玩家等级变化监听器
 *
 * @author : luoyong
 * @date : 2021-03-20 14:47
 **/
@Listener
public interface PlayerLevelChangedListener {
    //收到玩家等级的变化
    void onLevelChanged(PlayerActor player, int oldLevel, int newLevel);
}
