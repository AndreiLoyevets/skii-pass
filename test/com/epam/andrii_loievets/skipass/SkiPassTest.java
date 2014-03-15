package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SkiPassTest {

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
	public void block_NotBlocked_Blocks() {
		SkiPass sp = new SeasonSkiPass();
		
		sp.block();
		
		assertTrue("Skipass not blocked after block()", sp.isBlocked());
	}
	
	@Test
	public void constructor_Create2SkiPasses_IdUnique() {
		SkiPass sp1 = new SeasonSkiPass();
		SkiPass sp2 = new SeasonSkiPass();
		
		assertNotEquals("IDs are not unique", sp1.getID(), sp2.getID());
	}
}