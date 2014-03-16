package com.epam.andrii_loievets.skipass;

import java.util.Date;

public class Turnstile {
	private SkiPassSystem system;
	
	public void connect(SkiPassSystem system) {
		this.system = system;
	}

	public boolean isExpired(SkiPass sp) {
		Date currentDate = new Date(System.currentTimeMillis());
		
		return (currentDate.before(sp.getExpirationDate()) ? false : true);
	}

	public boolean isActivated(SkiPass sp) {
		Date currentDate = new Date(System.currentTimeMillis());
		
		return (currentDate.before(sp.getActivationDate()) ? false : true);
	}

	public boolean makePass(SkiPass sp) {
		if (sp.isBlocked()) {
			return false;
		}
		
		if (isExpired(sp)) {
			sp.block();
			return false;
		}
		
		if (!isActivated(sp)) {
			return false;
		}
		
		if (!system.validate(sp)) {
			sp.block();
			return false;
		}
		
		if (sp.pass()) {
			return true;
		} else {
			sp.block(); // card is already empty
			return false;
		}
	}
}