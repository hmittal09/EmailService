package com.example.mail.MailServiceKYC.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.MailServiceKYC.dto.MailRequest;
import com.example.mail.MailServiceKYC.dto.MailResponse;
import com.example.mail.MailServiceKYC.service.EmailService;

@RestController
public class MailController {
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/sendingEmail")
	public MailResponse kyc_DocumentVarified(@RequestBody MailRequest mailRequest)
	{
		Map<String, Object> model= new HashMap<String, Object>();
		model.put("Name", mailRequest.getName());
		
		return emailService.sendMail(mailRequest, model);
		
	}
	
	@PostMapping("/kyc_DocumentForClientReview")
	public MailResponse kyc_DocumentForClientReview(@RequestBody MailRequest mailRequest)
	{
		
		Map<String, Object> model= new HashMap<String, Object>();
		model.put("Name", mailRequest.getName());
		
		return emailService.sendDocument(mailRequest, model);
		
	}
}
