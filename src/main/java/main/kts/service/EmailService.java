package main.kts.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import main.kts.model.RegisteredUser;


@Service
public class EmailService {
	
	@Autowired
	private  JavaMailSender javaMailSender;

	//Use class for reading values from application.properties file
	@Autowired
	private Environment env;
	
	@Async
	public  void nofiticationForUpdateCulturalOffer(ArrayList<RegisteredUser> users, String nameOfCulturalOffer) throws Exception {
		
		try {
			for(RegisteredUser ru : users) {
				System.out.println("Sending email...");
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setTo(ru.getEmail());
				mail.setSubject("Notification for updating");
				mail.setText("The cultural offer "+ nameOfCulturalOffer + " is deleted.");
				javaMailSender.send(mail);
				System.out.println("Email sent!");
			}			
		}
		catch(Exception e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			throw new Exception("There is occured a problem with email.");
		}
	
	}
}
