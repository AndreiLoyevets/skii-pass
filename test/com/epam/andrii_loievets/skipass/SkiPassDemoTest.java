package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SkiPassDemoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void prepareSystem_CreateSystem_SystemNotNull() {
		SkiPassDemo spDemo = new SkiPassDemo();

		spDemo.prepareSystem();

		assertNotNull("SkiPassSystem not created", spDemo.getSystem());
	}

	@Test
	public void prepareSystem_InitSystem_ListOfTurnstiles() {
		SkiPassDemo spDemo = new SkiPassDemo();

		spDemo.prepareSystem();
		SkiPassSystem system = spDemo.getSystem();

		assertNotNull("SkiPassSystem not initialized", system.getTurnstile(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void passTurnstile_NullTunrstile_ThrowsException() {
		SkiPassDemo spDemo = new SkiPassDemo();

		spDemo.prepareSystem();
		spDemo.passTurnstile(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void passTurnstile_NullSkiPass_ThrowsException() {
		SkiPassDemo spDemo = new SkiPassDemo();

		spDemo.prepareSystem();
		spDemo.passTurnstile(spDemo.getSystem().getTurnstile(0), null);
	}

	@Test
	public void passTurnstile_LimitedMake1Pass_PassagesReducedBy1() {
		SkiPassDemo spDemo = new SkiPassDemo();

		spDemo.prepareSystem();

		SkiPassSystem system = spDemo.getSystem();
		Date activationDate = new Date(System.currentTimeMillis() - 10000);
		Date expirationDate = new Date(System.currentTimeMillis() + 10000);
		SkiPass card = system.createSkiPass("LIMITED", activationDate,
				expirationDate, 10);

		spDemo.passTurnstile(system.getTurnstile(0), card);

		assertEquals("Number of passages was not reduced by 1", 9,
				((LimitedSkiPass) card).getNumPassages());
	}
}
