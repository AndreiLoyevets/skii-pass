package com.epam.andrii_loievets.skipass;

import java.util.Date;

public class LimitedSkiPass extends SkiPass {
	private int numPassages;

	public LimitedSkiPass(int id, Date activationDate, Date expirationDate,
			int numPassages) {
		super(id, activationDate, expirationDate);
		if (numPassages <= 0) {
			throw new IllegalArgumentException(
					"Non-positive number of passages is not allowed");
		}
		this.numPassages = numPassages;
	}

	public int getNumPassages() {
		return numPassages;
	}

	@Override
	public boolean pass() {
		if (numPassages > 0) {
			--numPassages;
			return true;
		} else {
			return false;
		}
	}
}
