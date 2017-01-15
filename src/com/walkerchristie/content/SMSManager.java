package com.walkerchristie.content;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSManager extends HttpServlet  {
	private ConcurrentHashMap<String, RequestProcessor> sessions = new ConcurrentHashMap<String, RequestProcessor>();
	private static final long serialVersionUID = 6521584851387748583L;

	public static final String ACCOUNT_SID = "AC90b7d2008703ddfb9c8acc3274c1d924";
	public static final String AUTH_TOKEN = "9968159162bcf90e1c63c2860c0a0847";

	@Override
	public void init() throws ServletException {
		super.init();

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
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
			currentSession = new RequestProcessor();
		}

		new Thread(() -> {
			String answer = currentSession.respondTo(message);

			System.out.println("Msg: " + message);
			System.out.println("Answer: " + answer);
			System.out.println("From: " + phoneNum);
			sessions.put(phoneNum, currentSession);

			Message.creator(new PhoneNumber(phoneNum),  // to
					new PhoneNumber("+14159653726"),  // from
					answer)
			.create();
		}).start();
	}
}
