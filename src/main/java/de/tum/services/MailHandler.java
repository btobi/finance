package de.tum.services;

import de.tum.models.Mail;
import de.tum.repositories.MailRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MailHandler {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private StockService stockService;

    private List<Mail> mails = Lists.emptyList();

    @PostConstruct
    public void fillMailCache() {
        this.mails = mailRepository.findAll();
    }

    public void handleAll(Message[] messages) {
        Arrays.stream(messages).forEach(this::handle);
    }


    public void handle(Message message) {
        try {
            if (message.getSubject() != null && !message.getSubject().contains("Watchlist"))
                return;

            Date receivedDate = message.getReceivedDate();

            if (mails.stream().anyMatch(m -> m.getReceivedDate().getTime() == receivedDate.getTime())) {
                log.debug("Mail from {} already exists. Skip!", receivedDate);
                return;
            }

            log.debug("Handling Mail from {}.", receivedDate);

            handleAttachments(message);

            mailRepository.save(Mail.builder().receivedDate(receivedDate).build());

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAttachments(Message message) throws IOException, MessagingException {

        String contentType = message.getContentType();

        if (contentType.contains("multipart")) {

            Multipart multiPart = (Multipart) message.getContent();
            BufferedReader br;
            String line;
            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    handleAttachment(part);
                }
            }

        }

    }

    private void handleAttachment(MimeBodyPart part) throws MessagingException, IOException {

        BufferedReader br;
        String line;
        String fileName = part.getFileName();
        br = new BufferedReader(new InputStreamReader(part.getInputStream()));
        line = br.readLine();
        String stockDate = clean(line.split(";")[0]).replace("Depotbewertung vom", "");

        while (!br.readLine().startsWith("\"WP-Art\""));
        while ((line = br.readLine()) != null) {
            if (line.startsWith("\"\""))
                break;
            String[] values = line.split(";");
            String stockType  = clean(values[0]);
            String stockIsin  = clean(values[1]);
            String stockName  = clean(values[2]);
            String stockValue = clean(values[10]);
            stockService.handleStock(stockType, stockIsin, stockName, stockDate, stockValue);
        }
//        log.debug(fileName);
    }

    private static String clean(String val) {
        return val.replaceAll("^\"|\"$", "");
    }

}
