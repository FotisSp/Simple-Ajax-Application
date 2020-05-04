package org.webapp.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Fotis Spanopoulos
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat dateFormat = null;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String surname;
	private String gender;
	private Date birthdate = null;

	/**
	 * FtechType Lazy to load on demand , Eager to preload among all others
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, optional = true)
	private HomeAddress homeAdd;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, optional = true)
	private WorkAddress workAdd;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
		if (birthdate == null)
			return "";
		return dateFormat.format(birthdate);
	}
	
	@Transient
	public String defaultBirthdate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.format(birthdate);
		}catch (NullPointerException e) {
			return "";
		}
	}

	public void setBirthdate(String birthdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		try {
			this.birthdate = sdf.parse(birthdate);
			String tmpDate = dateFormat.format(this.birthdate);
			this.birthdate = dateFormat.parse(tmpDate);
		} catch (ParseException e) {
			try {
				this.birthdate = dateFormat.parse(birthdate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public HomeAddress getHomeAddress() {
		return homeAdd;
	}

	public void setHomeAddress(HomeAddress address) {
		homeAdd = address;
	}
	
	public WorkAddress getWorkAddress() {
		return workAdd;
	}

	public void setWorkAddress(WorkAddress address) {
		workAdd = address;
	}
}
