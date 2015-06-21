package events;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import models.Task;
import models.User;
import dao.TaskDAO;

@Singleton
public class EmailSender {

	@EJB
	private TaskDAO taskDao;
	
	@Schedule(dayOfWeek = "Sun", hour = "16", minute = "36")
	public void sendAutomaticEmail(Task task, User user) {
		String txt = "Some of the attributes of this task with id"
				+ task.getId() + "has beed changed by" + user.getUsername();
		String to = taskDao.getEmailOfExecutor(task);
		createMessage("Modification of current Task", txt, to);
	}

	public void createMessage(String subject, String text, String to) {
		String from = "galin.angelov5@yahoo.com";

		String host = "smtp.mail.yahoo.com";

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			message.setSubject(subject);

			// Now set the actual message
			message.setText(text);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
