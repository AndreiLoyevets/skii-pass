package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;

public class LimitedSkiPassTest {

    @Test
    public void pass_Make1Pass_NumPassagesDecreasedBy1() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        LimitedSkiPass sp = (LimitedSkiPass) system.createSkiPass("LIMITED",
                date, date, 10);

        sp.pass();
        assertEquals("10 passages after 1 passage are not decreased to 9", 9,
                sp.getNumPassages());
    }

    @Test
    public void pass_Make1PassEmptyCard_PassNotAllowed() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        SkiPass sp = system.createSkiPass("LIMITED", date, date, 1);
        sp.pass();

        assertFalse("Empty LimitedSkiPass accepted pass", sp.pass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_NegativeNumPassages_ThrowsIllegalArgumentException() {
        SkiPassSystem system = SkiPassSystem.getInstance();
        Date date = new Date();

        system.createSkiPass("LIMITED", date, date, -1);
    }
}
