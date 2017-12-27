package de.tum.services;

import de.tum.utils.ImapThread;
import de.tum.utils.ImapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.MessagingException;

@Service
@Slf4j
public class AsyncRunner {

    @Autowired
    private MailHandler mailHandler;

    @Async
    void startImapThread(Folder inbox, String username, String password) throws InterruptedException {
        log.debug("Start inbox listener");
        ImapThread idleThread = new ImapThread(inbox, username, password);
        idleThread.setDaemon(false);
        idleThread.start();
        idleThread.join();
    }

    @Async
    void checkExistingMails(Folder inbox, String username, String password) throws MessagingException, InterruptedException {
        ImapUtils.ensureOpen(inbox, username, password);
        log.debug("Check whole inbox");
        mailHandler.handleAll(inbox.getMessages());
    }

}
