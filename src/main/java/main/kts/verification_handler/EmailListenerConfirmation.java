package main.kts.verification_handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import main.kts.model.User;
import main.kts.model.VerificationToken;
import main.kts.service.EmailService;
import main.kts.service.VerificationTokenService;

@Component
public class EmailListenerConfirmation implements ApplicationListener<OnAccessLinkEvent>{
	
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void onApplicationEvent(OnAccessLinkEvent event) {
		this.confirmRegistration(event);
		
	}
	private void confirmRegistration(OnAccessLinkEvent event) {
		try {
			
			User user = event.getUser();
			
			String token = UUID.randomUUID().toString();
			
		    VerificationToken newUserToken = new VerificationToken(token, user);
		   
			tokenService.save(newUserToken);
						
			String recipient = user.getEmail();
			String subject = "Verification";
	        String url 
	          = event.getPath() + "/" + token;
	        
	        emailService.sendNotificaitionAsync(url, recipient, subject);
	        
			
		}catch(Exception e) {
			System.out.println("Error sending verification mail");
		}
		
	}
}
