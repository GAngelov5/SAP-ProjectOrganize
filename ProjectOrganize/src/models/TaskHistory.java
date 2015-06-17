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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TaskHistory")
@XmlRootElement
public class TaskHistory implements Serializable{
	
	private static final long serialVersionUID = 2481290208626581511L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name= "changes")
	private Task task;
	
	@OneToOne
	private User editor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	
	
}
