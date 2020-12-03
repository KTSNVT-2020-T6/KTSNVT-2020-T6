package main.kts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	//Use class for reading values from application.properties file
	@Autowired
	private Environment env;

}
