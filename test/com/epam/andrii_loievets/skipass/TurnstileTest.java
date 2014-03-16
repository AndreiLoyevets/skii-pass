package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class TurnstileTest {
	@Test
	public void isExpired_ExpiredCard_ReturnsTrue() {
		Turnstile t = new Turnstile();
		Date date = new Date(System.currentTimeMillis() - 10000);
		SkiPass sp = SkiPassSystem.getInstance().createSkiPass("LIMITED", date,
				date, 1);

		assertTrue("Turnstile accepted expired card", t.isExpired(sp));
	}

	@Test
	public void isActivated_NotActivatedCard_ReturnsFalse() {
		Turnstile t = new Turnstile();
		Date date = new Date(System.currentTimeMillis() + 10000);
		SkiPass sp = SkiPassSystem.getInstance().createSkiPass("LIMITED", date,
				date, 1);

		assertFalse("Turnstile accepted card which is not activated yet",
				t.isActivated(sp));
	}

	@Test
	public void makePass_Make1PassBlockedCard_ReturnsFalse() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		Date date = new Date(System.currentTimeMillis());
		SkiPass sp = system.createSkiPass("LIMITED", date, date, 1);

		sp.block();

		assertFalse("Turnstile accepted blocked card", t.makePass(sp));
	}

	@Test
	public void makePass_Make1PassExpiredCard_ReturnsFalse() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		Date date = new Date(System.currentTimeMillis() - 10000);
		SkiPass sp = system.createSkiPass("LIMITED", date, date, 1);

		assertFalse("Turnstile accepted expired card", t.makePass(sp));
	}

	@Test
	public void makePass_Make1PassNotActivatedCard_ReturnsFalse() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		Date date = new Date(System.currentTimeMillis() + 10000);
		SkiPass sp = system.createSkiPass("LIMITED", date, date, 1);

		assertFalse("Turnstile accepted not activated card", t.makePass(sp));
	}

	@Test
	public void makePass_Make1PassWrongID_ReturnsFalse() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		t.connect(system);
		Date activationDate = new Date(System.currentTimeMillis() - 10000);
		Date expirationDate = new Date(System.currentTimeMillis() + 10000);
		SkiPass sp = new SkiPass(123456, activationDate, expirationDate);

		assertFalse("Turnstile accepted invalid card ID", t.makePass(sp));
	}

	@Test
	public void makePass_NotEnoughPassages_ReturnsFalse() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		t.connect(system);
		Date activationDate = new Date(System.currentTimeMillis() - 10000);
		Date expirationDate = new Date(System.currentTimeMillis() + 10000);
		SkiPass sp = system.createSkiPass("LIMITED", activationDate,
				expirationDate, 1);
		
		t.makePass(sp);

		assertFalse("Turnstile accepted card with 0 passages", t.makePass(sp));
	}
	
	@Test
	public void makePass_ValidCard_ReturnsTrue() {
		SkiPassSystem system = SkiPassSystem.getInstance();
		Turnstile t = new Turnstile();
		t.connect(system);
		Date activationDate = new Date(System.currentTimeMillis() - 10000);
		Date expirationDate = new Date(System.currentTimeMillis() + 10000);
		SkiPass sp = system.createSkiPass("LIMITED", activationDate,
				expirationDate, 1);

		assertTrue("Turnstile rejected valid card", t.makePass(sp));
	}
}
