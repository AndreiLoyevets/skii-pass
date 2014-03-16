package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SkiPassTest {
	@Test
	public void block_NotBlocked_Blocks() {
		SkiPass sp = new SkiPass(0, null, null);
		
		sp.block();
		
		assertTrue("Ski-pass not blocked after block()", sp.isBlocked());
	}
}