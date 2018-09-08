package com.fivecents.backends.clientbrandnew.enterprise.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This bean stands for the enterprise tier object for a client.
 * 
 * @author Laurent CAILLETEAU
 */
@XmlRootElement(name="client", namespace="")
public class Client {
	private int id;
	private int legacyId;
	private String lastName;
	private String firstName;
	private Date birthDate;
	
	@XmlElement(required = true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@XmlElement(name = "legacy-id")
	public int getLegacyId() {
		return legacyId;
	}
	public void setLegacyId(int legacyId) {
		this.legacyId = legacyId;
	}
	@XmlElement(name = "lastname")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@XmlElement(name = "firstname")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@XmlElement(name = "birthdate")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
