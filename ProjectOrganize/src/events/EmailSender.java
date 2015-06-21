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
	
	@Schedule(dayOfWeek = "Mon", hour = "1", minute = "19")
	public void sendAutomaticEmail(Task task, User user) {
		String txt = "Some of the attributes of this task with id "
				+ task.getId() + " has beed changed by " + user.getUsername();
		String to = taskDao.getEmailOfExecutor(task);
		createMessage("Modification of current Task", txt, "galin.angelov5@gmail.com");
	}

	public void createMessage(String subject, String text, String to) {
		String from = "galin.angelov5@yahoo.com";

		String host = "smtp.mail.yahoo.com";

		Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new SmtpAuthenticator());

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
