package forum.services.impl;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.util.Properties;

@Transactional
public class MailService {

	public void sendMail(String to, String subject, String messageText) {

		String host = "smtp.zoznam.sk";
		String port = "587";

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(properties, null);

		String from = "movieforum@zoznam.sk";		
		String password = "JankoHrasko1";		

		Message msg = new MimeMessage(session);	

		try {
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			msg.setText(messageText);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, 587, from, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.printf("Mail sent. Recipient: %s Subject: %s %nMessage: %s", to, subject, messageText);
			transport.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
