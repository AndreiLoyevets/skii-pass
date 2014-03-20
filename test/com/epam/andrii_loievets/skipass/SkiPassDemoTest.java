package com.epam.andrii_loievets.skipass;

import java.util.Calendar;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

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

    @Test
    public void getSeasonSkiPass_NoParam_ReturnsValidSeasonSkiPass() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        assertNotNull("Cannot generate valid season ski-pass", spDemo.getSeasonSkiPass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLimitedSkiPass_NegativePassages_ThrowsException() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        spDemo.getLimitedSkiPass(-1);
    }

    @Test
    public void getLimitedSkiPass_ValidParam_ReturnsNotExpiredCard() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        SkiPassSystem system = spDemo.getSystem();
        Turnstile turnstile = system.getTurnstile(0);
        SkiPass skiPass = spDemo.getLimitedSkiPass(1);

        assertFalse("Limited ski-pass is expired", turnstile.isExpired(skiPass));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDaysSkiPass_NegativeDays_ThrowsException() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        spDemo.getDaysSkiPass(-1);
    }

    @Test
    public void getDaysSkiPass_5Days_InvalidAfter5Days() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        SkiPass skiPass = spDemo.getDaysSkiPass(5);
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 5);

        assertTrue("5-days ski-pass is not expired after 5 days",
                skiPass.getExpirationDate().before(calendar.getTime()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFirstHalfDaySkiPass_NullDay_ThrowsException() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        spDemo.getFirstHalfDaySkiPass(null);
    }

    @Test
    public void getFirstHalfDaySkiPass_TodayMorning_ReturnsNotNull() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 8, 0, 0);

        assertNotNull("Cannot get 1st half-day ski-pass",
                spDemo.getFirstHalfDaySkiPass(calendar.getTime()));
    }

    @Test
    public void getFirstHalfDaySkiPass_TodayMorning_CardValidAt1259() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 8, 0, 0);

        SkiPass skiPass = spDemo.getFirstHalfDaySkiPass(calendar.getTime());

        // Set time to 12:59
        calendar.add(Calendar.HOUR_OF_DAY, 4);
        calendar.add(Calendar.MINUTE, 59);

        assertTrue("SkiPass is invalid at 12:59, must be valid till 13:00",
                skiPass.getExpirationDate().after(calendar.getTime()));
    }

    @Test
    public void getFirstHalfDaySkiPass_TodayEvening_ReturnNull() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 14, 0, 0);

        SkiPass skiPass = spDemo.getFirstHalfDaySkiPass(calendar.getTime());

        assertNull("Created card for 9:00 - 13:00 when it is already 14:00",
                skiPass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSecondHalfDaySkiPass_NullDay_ThrowsException() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        spDemo.getSecondHalfDaySkiPass(null);
    }

    @Test
    public void getSecondHalfDaySkiPass_TodayMorning_ReturnsNotNull() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 8, 0, 0);

        assertNotNull("Cannot get 2nd half-day ski-pass",
                spDemo.getSecondHalfDaySkiPass(calendar.getTime()));
    }

    @Test
    public void getFirstHalfDaySkiPass_TodayMorning_CardValidAt1659() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 8, 0, 0);

        SkiPass skiPass = spDemo.getSecondHalfDaySkiPass(calendar.getTime());

        // Set time to 16:59
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        calendar.add(Calendar.MINUTE, 59);

        assertTrue("SkiPass is invalid at 16:59, must be valid till 17:00",
                skiPass.getExpirationDate().after(calendar.getTime()));
    }

    @Test
    public void getSecondHalfDaySkiPass_TodayEvening_ReturnNull() {
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        Calendar calendar = new GregorianCalendar(2014, 2, 20, 20, 0, 0);

        SkiPass skiPass = spDemo.getSecondHalfDaySkiPass(calendar.getTime());

        assertNull("Created card for 13:00 - 17:00 when it is already 20:00",
                skiPass);
    }
}
