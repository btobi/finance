package de.tum.services;

import com.sun.mail.imap.IMAPStore;
import de.tum.utils.ImapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

@Service
@Slf4j
public class MailListenerService {

    @Autowired
    private MailHandler mailHandler;

    @Autowired
    private AsyncRunner asyncRunner;

    @Value("${finance.mail.username}")
    private String username;

    @Value("${finance.mail.password}")
    private String password;

    public void runListener() {

        Session session = Session.getInstance(ImapUtils.getProperties());
        IMAPStore store = null;
        Folder inbox = null;

        try {
            store = (IMAPStore) session.getStore("imaps");

            store.connect(username, password);

            if (!store.hasCapability("IDLE")) {
                throw new RuntimeException("IDLE not supported");
            }

            inbox = store.getFolder("INBOX");
            inbox.addMessageCountListener(new MessageCountAdapter() {

                @Override
                public void messagesAdded(MessageCountEvent event) {
                    Message[] messages = event.getMessages();
                    mailHandler.handleAll(messages);
                }
            });

            asyncRunner.startImapThread(inbox, username, password);
            asyncRunner.checkExistingMails(inbox, username, password);

        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                log.debug("Application interrupted. Disconnect from Inbox");
            }
        } finally {
            ImapUtils.close(inbox);
            ImapUtils.close(store);
        }
    }

}