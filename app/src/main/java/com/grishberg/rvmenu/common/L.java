package com.grishberg.rvmenu.common;

import com.github.grishberg.consoleview.Logger;
import com.github.grishberg.idlehangler.IdleTask;
import com.github.grishberg.idlehangler.IdleTaskManager;
import com.github.grishberg.idlehangler.IdleTaskManagerImpl;

public class L {
    private final Logger log;
    private final IdleTaskManager idleTaskManager;

    public L(Logger l) {
        log = l;
        idleTaskManager = new IdleTaskManagerImpl();
    }

    public void d(String t, String m) {
        // TODO create pool.
        idleTaskManager.addTask(new LogTask(t, m));
    }

    private class LogTask implements IdleTask {
        String tag;
        String message;

        LogTask(String tag, String message) {
            this.tag = tag;
            this.message = message;
        }

        @Override
        public void run() {
            log.d(tag, message);
        }
    }
}
