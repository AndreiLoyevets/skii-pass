package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SkiPassSystemTest {

    @Test
    public void createSkiPass_NullDate_ReturnsNull() {
        SkiPassSystem system = SkiPassSystem.getInstance();

        SkiPass sp = system.createSkiPass("", null, null, 0);

        assertNull("SkiSystem doesn't check the date", sp);
    }

    @Test
    public void createSkiPass_NullCardType_ReturnsNull() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        SkiPass sp = system.createSkiPass(null, date, date, 0);

        assertNull("SkiSystem doesn't check the card type for null", sp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSkiPass_UnknownType_ThrowsException() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        system.createSkiPass("", date, date, 0);
    }

    @Test
    public void createSkiPass_SeasonCard_ReturnsSeasonSkiPass() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        SkiPass sp = system.createSkiPass("SEASON", date, date, 0);

        assertNotNull("SkiSystem doesn't know about season card type", sp);
    }

    @Test
    public void createSkiPass_HourlyCard_ReturnsHourlySkiPass() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        SkiPass sp = system.createSkiPass("HOURLY", date, date, 0);

        assertNotNull("SkiSystem doesn't know about hourly card type", sp);
    }

    @Test
    public void createSkiPass_LimitedCard_ReturnsLimitedSkiPass() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        SkiPass sp = system.createSkiPass("LIMITED", date, date, 10);

        assertNotNull("SkiSystem doesn't know about limited card type", sp);
    }

    @Test
    public void validate_WrongCardID_ReturnsFalse() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        SkiPass sp = mock(SkiPass.class);

        when(sp.getID()).thenReturn(123456);

        assertFalse("SkiSystem accepted card with invalid ID", system.validate(sp));
    }

    @Test
    public void init_CreateTurnstiles_ReturnsListOfTurnstiles() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        List<Turnstile> turnstiles = system.init();

        assertNotNull("System cannot instantiate turnstiles", turnstiles);
    }

    @Test
    public void getTurnstile_InvalidTurnstileID_ReturnsNull() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        system.init();

        assertNull("System returned a turnstile with invalid ID", system.getTurnstile(-1));
    }

    @Test
    public void getTurnstile_ValidTurnstileID_ReturnsTurnstile() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        system.init();

        assertNotNull("System cannot return a turnstile with valid ID", system.getTurnstile(0));
    }
}
