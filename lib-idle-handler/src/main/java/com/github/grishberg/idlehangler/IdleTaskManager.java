package com.github.grishberg.idlehangler;

public interface IdleTaskManager {
    void addTask(IdleTask task);
    void removeTask(IdleTask task);
}
