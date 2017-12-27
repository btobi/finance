package de.tum;

import de.tum.services.MailListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EntryPoint {

    @Autowired
    private MailListenerService mailListenerService;

    @PostConstruct
    public void run() {
        mailListenerService.runListener();
    }

}
