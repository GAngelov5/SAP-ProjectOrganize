package services;

import java.io.Serializable;

import models.Project;

public class ProjectContext implements Serializable{

	private static final long serialVersionUID = 1956839778550574444L;
	
	private Project currentProject;
	
	public Project getCurrentProject() {
		return currentProject;
	}
	
	public void setCurrentProject (Project currentProject) {
		this.currentProject = currentProject;
	}
}
