package com.github.grishberg.idlehangler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.MainThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

@MainThread
public class IdleTaskManagerImpl implements IdleTaskManager {
    private LinkedList<IdleTask> tasks = new LinkedList<>();
    private CustomIdleHandler idleHandler = new CustomIdleHandler();
    private MessageQueue messageQueue;

    public IdleTaskManagerImpl() {
        messageQueue = Looper.getMainLooper().getQueue();
    }

    @Override
    public void addTask(IdleTask task) {
        if (tasks.isEmpty()) {
            messageQueue.addIdleHandler(idleHandler);
        }
		tasks.offer(task);
    }
	
	public void removeTask(IdleTask task){
		tasks.remove(task);
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
