package com.github.grishberg.idlehangler;

import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.MainThread;

import java.util.LinkedList;

@MainThread
public class IdleTaskManagerImpl implements IdleTaskManager {
    private LinkedList<IdleTask> tasks = new LinkedList<>();
    private CustomIdleHandler idleHandler = new CustomIdleHandler();
    private MessageQueue messageQueue = Looper.getMainLooper().getQueue();

    @Override
    public void addTask(IdleTask task) {
        if (tasks.isEmpty()) {
            messageQueue.addIdleHandler(idleHandler);
        }
        tasks.offer(task);
    }

    @Override
    public void removeTask(IdleTask task) {
        tasks.remove(task);
    }

    private class CustomIdleHandler implements MessageQueue.IdleHandler {
        @Override
        public boolean queueIdle() {
            if (tasks.isEmpty()) {
                return false;
            }

            for (IdleTask task : tasks) {
                task.run();
            }

            tasks.clear();

            return false;
        }
    }
}
