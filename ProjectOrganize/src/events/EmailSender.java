package events;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
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
	
	@Resource
	TimerService timerService;
	
	@Timeout
	public void sendAutomaticEmail(Timer timer, Task task, User user) {
		System.out.println("Time remaining: " + timer.getTimeRemaining());
		String txt = "Some of the attributes of this task with id "
				+ task.getId() + " has beed changed by " + user.getUsername();
		//String to = taskDao.getEmailOfExecutor(task);
		createMessage("Modification of current Task", txt, "galin.tester@yahoo.com");
	}
	
	public Timer setTimer(long duration) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 20);
		cal.set(Calendar.MINUTE, 0);
		Date currentDate = cal.getTime();
		System.out.println("CurrentDate: " + currentDate);
		System.out.println("CurrentDate in millisec: " + currentDate.getTime());
		System.out.println("time until its send: " + ((currentDate.getTime()) - duration)/1000);
		Timer timer = timerService.createSingleActionTimer(currentDate.getTime() - duration, new TimerConfig());
		Timer timer1 = timerService.createSingleActionTimer(duration + 10000, new TimerConfig());
		return timer1;
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
