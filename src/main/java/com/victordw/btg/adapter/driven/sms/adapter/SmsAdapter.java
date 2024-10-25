package com.victordw.btg.adapter.driven.sms.adapter;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.victordw.btg.configuration.Constants;
import com.victordw.btg.domain.spi.ISendNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsAdapter implements ISendNotificationPort {

	@Override
	public void sendNotification(String... arg) {

		String phoneTo = arg[Constants.FIRST_POSITION];
		String message = arg[Constants.TWO_POSITION];

		Message.creator(
				new PhoneNumber(phoneTo),
				new PhoneNumber(Constants.TWILIO_FROM_PHONE_DEFAULT),
				message)
		.create();

	}
}
