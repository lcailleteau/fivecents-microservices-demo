package com.fivecents.backends.clientlegacy.enterprise.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This bean stands for the enterprise tier object for a client.
 * 
 * @author Laurent CAILLETEAU
 */
@XmlRootElement(name="client", namespace="com.fivecents.clients")
public class Client {
	private int id;
	private Address address;
	
	@XmlElement(required = true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement(name = "address")
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
