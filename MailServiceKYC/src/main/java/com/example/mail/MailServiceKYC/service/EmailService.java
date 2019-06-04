package com.example.mail.MailServiceKYC.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties.Content;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.mail.MailServiceKYC.dto.MailRequest;
import com.example.mail.MailServiceKYC.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration config;

	public MailResponse sendMail(MailRequest mailRequest, Map<String, Object> model) {
		MailResponse mailResponse = new MailResponse();
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// helper.addAttachment("banner.jpg", new ClassPathResource("banner.jpg"));
			Template template = config.getTemplate("email.template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(mailRequest.getTo());
			helper.setText(html, true);
			helper.setSubject(mailRequest.getSubject());
			javaMailSender.send(message);

			mailResponse.setMessage("mail send to :" + mailRequest.getTo());
			mailResponse.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			mailResponse.setMessage("mail sending failure :" + e.getMessage());
			mailResponse.setStatus(Boolean.FALSE);

		}

		return mailResponse;

	}

	public MailResponse sendDocument(MailRequest mailRequest, Map<String, Object> model) {
		// TODO Auto-generated method stub
		MailResponse mailResponse = new MailResponse();
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			byte[] decoded = java.util.Base64.getDecoder().decode(mailRequest.getFile());

			ByteArrayDataSource ds = new ByteArrayDataSource(decoded,
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			
			helper.addAttachment("temp.docx", ds);

			Template template = config.getTemplate("email.template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(mailRequest.getTo());
			helper.setText(html, true);
			helper.setSubject(mailRequest.getSubject());
			javaMailSender.send(message);

			mailResponse.setMessage("mail send to :" + mailRequest.getTo());
			mailResponse.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			mailResponse.setMessage("mail sending failure :" + e.getMessage());
			mailResponse.setStatus(Boolean.FALSE);

		}

		return mailResponse;
	}

}
