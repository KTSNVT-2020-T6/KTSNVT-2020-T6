package main.kts.controller;


import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.model.User;
import main.kts.model.VerificationToken;
import main.kts.service.VerificationTokenService;

@RestController
@RequestMapping(value="api/verification")
public class VerificationTokenController {
	
	@Autowired
	private VerificationTokenService verificationService;
	
	
	@RequestMapping(value = "/{token}", method = RequestMethod.GET)
	public String confirmRegistration(@PathVariable String token,HttpServletRequest request) {
      
		VerificationToken verificationToken = verificationService.findByToken(token);
		if(verificationToken == null)
		{
			return "redirect: access denied";
		}
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();

		user.setVerified(true);
		verificationService.save(user);

		return "ok";
	}
}
