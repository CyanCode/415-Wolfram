package com.walkerchristie.content;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

public class SMSManager extends HttpServlet  {
	private static final long serialVersionUID = 6521584851387748583L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Started");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message sms = new Message
				.Builder()
				.body(new Body("The Robots are coming! Head for the hills!"))
				.build();

		MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
		response.setContentType("application/xml");

		try {
			response.getWriter().print(twiml.toXml());
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
	}
}
