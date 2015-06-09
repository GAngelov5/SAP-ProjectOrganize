package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = -2118772082181521097L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAndHour;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "TaskId")
	private Task task;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateAndHour() {
		return dateAndHour;
	}

	public void setDateAndHour(Date dateAndHour) {
		this.dateAndHour = dateAndHour;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
