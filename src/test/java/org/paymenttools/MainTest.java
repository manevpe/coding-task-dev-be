package org.paymenttools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.paymenttools.Main.getAllEntriesForDay;
import static org.paymenttools.Main.printLoyalCustomer;

class MainTest {

    @Test
    void getAllEntriesForDayParsesTheCsvCorrectly() throws Exception {
        assertEquals(getAllEntriesForDay(1).size(), 20);
    }

    @Test
    void checkLoyalCustomers() throws Exception {
        Main.main(new String[]{});
        assertEquals(List.of("1", "3", "4", "5"), printLoyalCustomer());
    }
}