package com.student.base.component.event;

public interface GameListener extends Comparable {

    void onGameEvent(GameEvent event);

    Class<? extends GameEvent> getEventType();

}
