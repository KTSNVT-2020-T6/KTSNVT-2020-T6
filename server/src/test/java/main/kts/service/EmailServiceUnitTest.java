package main.kts.service;

import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_ACTIVE;
import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_EMAIL;
import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_FIRST_NAME;
import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_LAST_NAME;
import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_PASSWORD;
import static main.kts.constants.EmailConstants.NEW_REGISTERED_USER_VERIFIED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.jvnet.mock_javamail.Mailbox;
import main.kts.model.RegisteredUser;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class EmailServiceUnitTest {

	@Autowired
	private EmailService emailService;
	
    private MimeMessage mimeMessage;
    
    @Autowired
	private JavaMailSender javaMailSender;
   
    @Before
    public void before() {
        mimeMessage = Mockito.mock(MimeMessage.class);
        javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    public void sendingEmailTest() throws Exception {
  
        ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
        RegisteredUser ru= new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME, 
				NEW_REGISTERED_USER_LAST_NAME,
				NEW_REGISTERED_USER_EMAIL,
				NEW_REGISTERED_USER_PASSWORD,
				NEW_REGISTERED_USER_ACTIVE,
				NEW_REGISTERED_USER_VERIFIED);
        String nameOfCulturalOffer = "cococo";
        users.add(ru);
        emailService.nofiticationForDeleteCulturalOffer(users, nameOfCulturalOffer);
    
        assertEquals(1, Mailbox.get("zamrsisu@gmail.com").getNewMessageCount());
        Message message = Mailbox.get("zamrsisu@gmail.com").get(0);
        assertEquals("Notification for updating", message.getSubject());
        assertEquals("zamrsisu@gmail.com", message.getRecipients(RecipientType.TO)[0].toString());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
}