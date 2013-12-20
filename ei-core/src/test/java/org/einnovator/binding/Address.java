/**
 * 
 */
package org.einnovator.binding;

import org.einnovator.format.ComparedObject;

/**
 * AA Address.
 *
 * @author Jorge Simão
 */
public class Address extends ComparedObject {
	protected String street;
	
	protected String city;
	
	protected String zip;
	
	protected String country;

	//
	// Constructors
	//
	
	/**
	 * Create instance of Address.
	 *
	 */
	public Address() {
	}

	/**
	 * Create instance of Address.
	 *
	 * @param street
	 * @param city
	 * @param zip
	 * @param country
	 */
	public Address(String street, String city, String zip, String country) {
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}

	/**
	 * Create instance of Address.
	 *
	 * @param city
	 * @param country
	 */
	public Address(String city,String country) {
		this.city = city;
		this.country = country;
	}

	//
	// Getters and setters
	//
	
	
	/**
	 * Get the value of street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Set the value of street.
	 *
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Get the value of city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the value of city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get the value of zip.
	 *
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Set the value of zip.
	 *
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Get the value of country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set the value of country.
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	
	
}
