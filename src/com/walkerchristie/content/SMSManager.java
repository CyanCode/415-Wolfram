package com.walkerchristie.content;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

public class SMSManager extends HttpServlet  {
	private HashMap<String, RequestProcessor> sessions = new HashMap<>();
	private static final long serialVersionUID = 6521584851387748583L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Started");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>Hey</h1>");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNum = request.getParameter("From");
		String message = request.getParameter("Body");
		RequestProcessor currentSession;
		
		if (sessions.containsKey(phoneNum)) {
			currentSession = sessions.get(phoneNum);
		} else {
			currentSession = new RequestProcessor(message);
		}
		
		
		
		Message sms = new Message
				.Builder()
				.body(new Body("The Robots are coming! Head for the hills!"))
				.build();

		String num = request.getParameter("From");
		String body = request.getParameter("Body");
		
		System.out.println("TEST");
		MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
		response.setContentType("application/xml");

		try {
			response.getWriter().print(twiml.toXml());
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
	}
}
