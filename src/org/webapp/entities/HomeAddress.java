package org.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Fotis Spanopoulos
 *
 */
@Entity
@Table(name = "home_address")
public class HomeAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
    @Column(name="homeAddress", length=255)
	private String address;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String homeAddress) {
		this.address = homeAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
