package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@ManyToOne
	@JoinColumn(name="ExecutorID")
	private User executor;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "ProjectID")
	private Project project;

	@OneToMany(mappedBy = "task")
	private List<Comment> comment;
	
	@ManyToOne
	@JoinColumn(name = "ReporterID")
	private User reporter;
	
	@OneToMany(mappedBy ="currentTask")
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

	public User getExecutor() {
		return executor;
	}

	public void setExecutor(User executor) {
		this.executor = executor;
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

	public List<TaskHistory> getUpdates() {
		return updates;
	}

	public void setUpdates(List<TaskHistory> updates) {
		this.updates = updates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((dateOfExectuon == null) ? 0 : dateOfExectuon.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (dateOfExectuon == null) {
			if (other.dateOfExectuon != null)
				return false;
		} else if (!dateOfExectuon.equals(other.dateOfExectuon))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
	
}
