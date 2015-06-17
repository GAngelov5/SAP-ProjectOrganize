package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "Task")
public class Task implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfExectuon;
	
	@ManyToMany
	@JoinTable(
		      name="Task_User",
		      joinColumns={@JoinColumn(name="TaskId", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="UserId", referencedColumnName="id")})
	private List<User> users;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "ProjectID")
	private Project project;

	@OneToMany(mappedBy = "task")
	private List<Comment> comment;
	
	@ManyToOne
	@JoinColumn(name = "ReporterID")
	private User reporter;
	
	@OneToMany(mappedBy ="task")
	private List<TaskHistory> updates;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfExectuon() {
		return dateOfExectuon;
	}

	public void setDateOfExectuon(Date dateOfExectuon) {
		this.dateOfExectuon = dateOfExectuon;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}
	
	
	
}
