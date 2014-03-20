package com.epam.andrii_loievets.skipass;

import java.util.Date;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SkiPassTest {

    @Test
    public void block_NotBlocked_Blocks() {
        SkiPass sp = new SkiPass(0, "HOURLY", new Date(), new Date());

        sp.block();

        assertTrue("Ski-pass not blocked after block()", sp.isBlocked());
    }
}
