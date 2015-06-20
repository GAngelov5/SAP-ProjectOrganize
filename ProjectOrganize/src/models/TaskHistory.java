package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TaskHistory")
public class TaskHistory implements Serializable{
	
	private static final long serialVersionUID = 2481290208626581511L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name= "changes")
	private Task currentTask;
	
	@OneToOne
	private User editor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public TaskHistory() {};
	
	public TaskHistory(Task t){
		this.currentTask = t;
	}
	
	public Task getTask() {
		return currentTask;
	}

	public void setTask(Task task) {
		this.currentTask = task;
	}

	public User getEditor() {
		return editor;
	}

	public void setEditor(User editor) {
		this.editor = editor;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
