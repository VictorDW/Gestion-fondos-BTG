package com.victordw.btg.adapter.driven.mail.adapter;

import com.victordw.btg.configuration.Constants;
import com.victordw.btg.domain.spi.ISendNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAdapter implements ISendNotificationPort {

	private final JavaMailSender mailSender;

	@Override
	public void sendNotification(String... arg) {

		String to = arg[Constants.FIRST_POSITION];
		String subject = arg[Constants.TWO_POSITION];
		String message = arg[Constants.THREE_POSITION];

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}
}
