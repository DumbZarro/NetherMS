package nether.application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import nether.dao.User;

public class DBUser {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty deathCause;
	private final SimpleStringProperty reincarnation;
	private final SimpleStringProperty operation;
	
	public DBUser(User user){//通过user来构造DBUser
		this.id = new SimpleIntegerProperty(user.getId());
		this.name = new SimpleStringProperty(user.getUsername());
		this.deathCause = new SimpleStringProperty(user.getDeathCause());
		this.reincarnation = new SimpleStringProperty(user.getReincarnation());	
		this.operation = new SimpleStringProperty("操作");	
	}
	public DBUser(){
		this.id = new SimpleIntegerProperty(1);
		this.name = new SimpleStringProperty("Test");
		this.deathCause = new SimpleStringProperty("death");
		this.reincarnation = new SimpleStringProperty("reborn");	
		this.operation = new SimpleStringProperty("操作");	
	}
	public DBUser(Integer id,String name,String deathCause,String reincarnation){
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.deathCause = new SimpleStringProperty(deathCause);
		this.reincarnation = new SimpleStringProperty(reincarnation);	
		this.operation = new SimpleStringProperty("操作");	
	}
	
	public SimpleIntegerProperty getId() {
		return id;
	}
	public SimpleStringProperty getName() {
		return name;
	}
	public SimpleStringProperty getDeathCause() {
		return deathCause;
	}
	public SimpleStringProperty getReincarnation() {
		return reincarnation;
	}
	public SimpleStringProperty getOperation() {
		return operation;
	}
}
