package org.paymenttools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.paymenttools.Main.getAllEntriesForDay;

class MainTest {

    @Test
    void getAllEntriesForDayParsesTheCsvCorrectly() throws Exception {
        assertEquals(getAllEntriesForDay(1).size(), 20);
    }
}