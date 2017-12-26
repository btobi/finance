package de.tum.repositories;

import de.tum.models.Mail;
import de.tum.models.StockValue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MailRepository extends CrudRepository<Mail, Integer> {

    List<Mail> findAll();

}
