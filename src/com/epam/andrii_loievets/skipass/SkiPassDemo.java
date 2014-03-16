package com.epam.andrii_loievets.skipass;


public class SkiPassDemo {
	private SkiPassSystem system;

	public void prepareSystem() {
		system = SkiPassSystem.getInstance();
		system.init();
	}

	public SkiPassSystem getSystem() {
		return system;
	}

	public void passTurnstile(Turnstile turnstile, SkiPass card) {
		if (turnstile == null) {
			throw new IllegalArgumentException("Turnstile is null");
		}
		
		if (card == null) {
			throw new IllegalArgumentException("Ski-pass is null");
		}
		
		turnstile.makePass(card);
	}
}