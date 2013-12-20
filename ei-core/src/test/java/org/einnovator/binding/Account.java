package org.einnovator.binding;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.einnovator.format.ComparedObject;
import org.einnovator.meta.Compare;


public class Account extends ComparedObject {

	public static Account instance =
		new Account(1L, "Jack", "jackpass", new Date(), 180.5f,
			10, true, Sex.MALE,
			"It's Jack we are talking about...",
			new Color(255,0,0),
			"jack@jcraft.org", new String[] {"jack@jland.org", "jack@jworld.org"},
			new BigDecimal(100));

	public static Account instance2 =
			new Account(2L, "Mary", "marysecret", new Date(), 170.5f,
				10, false, Sex.FEMALE,
				"It's all about Mary...",
				new Color(0, 255,0),
				"mary@jcraft.org", new String[] {"mary@jland.org", "mary@jworld.org"},
				new BigDecimal(1000));

	public static Account instance3 =
			new Account(3L, "Alice", "aliceX", new Date(), 170.5f,
				10, false, Sex.FEMALE,
				"Alice is looking for Bob...",
				new Color(0, 255,0),
				"mary@jcraft.org", new String[] {"mary@jland.org", "mary@jworld.org"},
				new BigDecimal(1000));

	public static Account[] instances = new Account[] { instance, instance2, instance3 };


	protected Long id;
	
	protected String name;

	protected String password;	

	protected String email;
	
	protected String[] otherEmails;

	protected Date birthday;

	protected BigDecimal balance;

	protected float height;

	protected int type;

	protected Boolean newsletter;
	
	protected Sex sex;
	
	protected String note;

	protected Color favoriteColor;	

	@Compare(false)
	protected Address address;

	@Compare(false)
	protected Set<String> titles = new HashSet<String>();

	@Compare(false)
	protected List<String> otherTitles = new ArrayList<String>();

	@Compare(false)
	protected List<Address> otherAddresses = new ArrayList<Address>();
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of Account.
	 *
	 */
	public Account() {
	}

	public Account(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Account(Long id, String name, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;		
	}
	
	public Account(Long id, String name, String password, Date birthday, float height,
			int type, Boolean newsletter, Sex sex, String note, Color favoriteColor,
			String email, String[] otherEmails, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.height = height;
		this.type = type;
		this.newsletter = newsletter;
		this.sex = sex;
		this.note = note;
		this.favoriteColor = favoriteColor;
		this.email = email;
		this.otherEmails = otherEmails;
		this.balance = balance;		
	}


	//
	// Getters and setters
	//
	
	/**
	 * Get the value of id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * Set the value of id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Get the value of name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the value of password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the value of birthday.
	 *
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Set the value of birthday.
	 *
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Get the value of height.
	 *
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Set the value of height.
	 *
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Get the value of type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the value of type.
	 *
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Get the value of note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Get the value of newsletter.
	 *
	 * @return the newsletter
	 */
	public Boolean getNewsletter() {
		return newsletter;
	}

	/**
	 * Set the value of newsletter.
	 *
	 * @param newsletter the newsletter to set
	 */
	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}


	/**
	 * Set the value of note.
	 *
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Get the value of favoriteColor.
	 *
	 * @return the favoriteColor
	 */
	public Color getFavoriteColor() {
		return favoriteColor;
	}

	/**
	 * Set the value of favoriteColor.
	 *
	 * @param favoriteColor the favoriteColor to set
	 */
	public void setFavoriteColor(Color favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

	
	/**
	 * Get the value of email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the value of email.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Get the value of emails.
	 *
	 * @return the emails
	 */
	public String[] getOtherEmails() {
		return otherEmails;
	}

	/**
	 * Set the value of emails.
	 *
	 * @param emails the emails to set
	 */
	public void setOtherEmails(String[] otherEmails) {
		this.otherEmails = otherEmails;
	}



	/**
	 * Get the value of sex.
	 *
	 * @return the sex
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * Set the value of sex.
	 *
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * Get the value of balance.
	 *
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Set the value of balance.
	 *
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	
	/**
	 * Get the value of address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the value of address.
	 *
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * Get the value of property {@code titles}.
	 *
	 * @return the titles
	 */
	public Set<String> getTitles() {
		return titles;
	}

	/**
	 * Set the value of property {@code titles}.
	 *
	 * @param titles the titles to set
	 */
	public void setTitles(Set<String> titles) {
		this.titles = titles;
	}
	
	/**
	 * Get the value of property {@code otherTitles}.
	 *
	 * @return the otherTitles
	 */
	public List<String> getOtherTitles() {
		return otherTitles;
	}

	/**
	 * Set the value of property {@code otherTitles}.
	 *
	 * @param otherTitles the otherTitles to set
	 */
	public void setOtherTitles(List<String> otherTitles) {
		this.otherTitles = otherTitles;
	}

	/**
	 * Get the value of property {@code otherAddresses}.
	 *
	 * @return the otherAddresses
	 */
	public List<Address> getOtherAddresses() {
		return otherAddresses;
	}

	/**
	 * Set the value of property {@code otherAddresses}.
	 *
	 * @param otherAddresses the otherAddresses to set
	 */
	public void setOtherAddresses(List<Address> otherAddresses) {
		this.otherAddresses = otherAddresses;
	}	

	//
	// Sample instance and static utility
	//
	

	public static Account get(Long id) {
		for (Account account: instances) {
			if (account.getId().equals(id)) {
				return account;
			}
		}
		return null;
	}
	
}


