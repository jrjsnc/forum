/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services.impl;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.util.Properties;


/**
 * The Class MailService.
 * This class is responsible for mail notification
 */
@Transactional
public class MailService {

	/**
	 * This method sends mail.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param messageText the message text
	 */
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
