package de.tum.utils;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.Folder;

public class ImapThread extends Thread {
    private final Folder folder;
    private volatile boolean running = true;
    private final String username;
    private final String password;

    public ImapThread(Folder folder, String username, String password) {
        super();
        this.folder = folder;
        this.username = username;
        this.password = password;
    }

    public synchronized void kill() {
        if (!running)
            return;
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ImapUtils.ensureOpen(folder, username, password);
                ((IMAPFolder) folder).idle();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }

        }
    }
}
