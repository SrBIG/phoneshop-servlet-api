package com.es.phoneshop.model.defence.dos;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DosServiceImplTest {
    private String id = "id";
    private String blockId = "id-lock";

    private DosService dosService;

    @Before
    public void setup() {
        dosService = DosServiceImpl.getInstance();
    }

    @Test
    public void testIsAllowedCountBlock() {
        int count = 0;
        while (dosService.isAllowed(id)){
            count++;
        }
        assertEquals(count, 19);
    }

//    @Test
//    public void testIsAllowedUnblock() throws InterruptedException {
//        while (dosService.isAllowed(blockId)){
//        }
//        sleep(61000);
//        assertTrue(dosService.isAllowed(blockId));
//    }
}
