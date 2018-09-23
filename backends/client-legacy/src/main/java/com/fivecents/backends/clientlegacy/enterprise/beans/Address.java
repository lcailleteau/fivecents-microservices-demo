package com.fivecents.backends.clientlegacy.enterprise.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This bean stands for the enterprise tier object for the address of a client.
 * 
 * @author Laurent CAILLETEAU
 */
@XmlRootElement(name="Address")
public class Address {
	private String street;
	private String city;
	private String zip;
	private String country;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
