//package models;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement
//@Entity
//@Table(name = "Role")
//public class Role implements Serializable{
//	
//	private static final long serialVersionUID = 7316459420269353800L;
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int id;
//	
//	private String name;
//	
//	public Role() {
//		
//	}
//	
//	public Role (String name) {
//		this.name = name;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
//	
//	
//	
//	
//}
