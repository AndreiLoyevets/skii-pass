package com.epam.andrii_loievets.skipass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides methods to produce new skipass cards, block cards, holds
 * data about turnstiles.
 * 
 * @author Andrii Loievets
 * @version 1.0 15-March-2014
 * 
 */
public class SkiPassSystem {
	private final int NUM_TURNSTILES = 10;
	private static SkiPassSystem instance;
	private int skiPassIDCounter;
	private List<Turnstile> turnstiles;

	private enum SkiPassType {
		SEASON, HOURLY, LIMITED
	}

	private SkiPassSystem() {
	}

	public static synchronized SkiPassSystem getInstance() {
		if (instance == null) {
			instance = new SkiPassSystem();
		}

		return instance;
	}

	public SkiPass createSkiPass(String cardType, Date activationDate,
			Date expirationDate, int numPassages) {

		if (cardType == null || activationDate == null
				|| expirationDate == null) {
			return null;
		}

		SkiPassType skiPassType = SkiPassType.valueOf(cardType);

		switch (skiPassType) {
		case SEASON:
			return new SkiPass(skiPassIDCounter++, activationDate,
					expirationDate);
		case HOURLY:
			return new SkiPass(skiPassIDCounter++, activationDate,
					expirationDate);
		case LIMITED:
			return new LimitedSkiPass(skiPassIDCounter++, activationDate,
					expirationDate, numPassages);
		default:
			return null;
		}
	}

	public boolean validate(SkiPass sp) {
		int id = sp.getID();

		if (id < 0 || id >= skiPassIDCounter) {
			return false;
		} else {
			return true;
		}
	}

	public synchronized List<Turnstile> init() {
		if (turnstiles == null) {
			turnstiles = new ArrayList<Turnstile>(NUM_TURNSTILES);
			for (int i = 0; i < NUM_TURNSTILES; ++i) {
				Turnstile turnstile = new Turnstile();
				turnstile.connect(this);
				turnstiles.add(turnstile);
			}
		}

		return turnstiles;
	}

	public Turnstile getTurnstile(int id) {
		if (id < 0 || id >= turnstiles.size()) {
			return null;
		} else {
			return turnstiles.get(id);
		}
	}
}