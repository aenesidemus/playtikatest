package com.burlaka.playtikatest.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "USERS")
public class User {

	public User() {
	}

	public User(String login) {
		this.login = login;
		setStime(new Date());
		setSutime(new Date());
		setLtime(new Date());
		setStatus("i");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "login")
	private String login;

	/**
	 * status of login/logout
	 */
	@Column(name = "status")
	private String status;

	/**
	 * time of last new message sync of this user
	 */
	@Column
	@Type(type = "timestamp")
	private Date stime;

	/**
	 * time of last new users sync of this user
	 */

	@Column
	@Type(type = "timestamp")
	private Date sutime;

	@Column
	@Type(type = "timestamp")
	private Date ltime;

	@OneToMany(mappedBy = "user")
	private Set<Message> messages;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSutime() {
		return sutime;
	}

	public void setSutime(Date sutime) {
		this.sutime = sutime;
	}

	public Date getLtime() {
		return ltime;
	}

	public void setLtime(Date ltime) {
		this.ltime = ltime;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> items1) {
		this.messages = items1;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
