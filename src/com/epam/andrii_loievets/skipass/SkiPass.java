package com.epam.andrii_loievets.skipass;

import java.util.Date;

public class SkiPass {
	private int id; // unique card ID
	private boolean blocked;
	private Date activationDate;
	private Date expirationDate;
	
	public SkiPass(int id, Date activation, Date expiration) {
		this.id = id;
		expirationDate = expiration;
		activationDate = activation;
	}
	
	public int getID() {
		return id;
	}
	
	public Date getActivationDate() {
		return activationDate;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void block() {
		blocked = true;
	}
	
	/**
	 * Make 1 passage. Should be overridden if some additional actions
	 * required.
	 * 
	 * @return true if the card is activated, false - otherwise
	 */
	public boolean pass() {
		return true;
	}
}