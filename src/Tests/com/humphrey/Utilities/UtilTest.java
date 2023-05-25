package com.humphrey.Utilities;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void shouldReturnOneHourEarlier() {
        LocalTime t = LocalTime.of(14,0);
        assertEquals(LocalTime.of(13,0), Util.convertESTtoLocalZone(t));
    }
    @Test
    void shouldFail() {
        LocalTime t = LocalTime.of(14,0);
        assertEquals(LocalTime.of(12,0), Util.convertESTtoLocalZone(t));
    }
}