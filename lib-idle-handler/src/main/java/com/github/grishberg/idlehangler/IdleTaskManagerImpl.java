package com.github.grishberg.idlehangler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.MainThread;

import java.util.ArrayList;
import java.util.Iterator;

@MainThread
public class IdleTaskManagerImpl implements IdleTaskManager {
    private ArrayList<IdleTask> tasks = new ArrayList<>();
    private CustomIdleHandler idleHandler = new CustomIdleHandler();
    private boolean subscribed;
    private MessageQueue messageQueue;
    private Handler mainThreadHandler;

    public IdleTaskManagerImpl() {
        messageQueue = Looper.getMainLooper().getQueue();
        mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                execute();
            }
        };
    }

    @Override
    public void addTask(IdleTask task) {
        tasks.add(task);
        if (!subscribed) {
            subscribe();
            subscribed = true;
        }
        if (messageQueue.isIdle()) {
            mainThreadHandler.sendEmptyMessage(0);
        }
    }

    private void subscribe() {
        messageQueue.addIdleHandler(idleHandler);
    }

    private void execute() {
        if (tasks.isEmpty()) {
            return;
        }

        Iterator<IdleTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            IdleTask task = iterator.next();
            task.run();
            iterator.remove();
        }
    }

    private class CustomIdleHandler implements MessageQueue.IdleHandler {
        @Override
        public boolean queueIdle() {
            execute();
            return false;
        }
    }
}
