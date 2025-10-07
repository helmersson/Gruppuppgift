package com.example.decathlon.deca;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Boundary value tests for all classes in the deca package.
 * One consolidated test class to keep coverage centralized.

 * Notes about approach:
 * - Track (time-based) and field (distance/height-based).
 * - For track events, a lower time is better; valid intervals are [min, max] inclusive as per each event class.
 * - For field events, a longer distance/height is better; valid intervals are [min, max] inclusive as per each event class.
 */
@DisplayName("Decathlon (Deca) boundary value suite")
@Tag("deca")
@Tag("boundary")
public class DecaBoundaryValueTest {

    @Test
    @DisplayName("100m: validates bounds and exceptions")
    @Tag("100m")
    void deca100m_boundaries() throws Exception {
        // Event: 100m (track). Valid range (inclusive): [5.0s, 20.0s]
        Deca100M ev = new Deca100M();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(4.999));
        // At min should be accepted and non-negative
        assertDoesNotThrow(() -> ev.calculateResult(5.0));
        assertTrue(ev.calculateResult(5.0) >= 0);
        // Just above min within range
        assertTrue(ev.calculateResult(5.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(12.0) >= 0);
        // Just below max within range
        assertTrue(ev.calculateResult(19.999) >= 0);
        // At max should be accepted
        assertTrue(ev.calculateResult(20.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(20.001));
    }

    @Test
    @DisplayName("110m Hurdles: validates bounds and exceptions")
    @Tag("110mHurdles")
    void deca110mHurdles_boundaries() throws Exception {
        // Event: 110m Hurdles (track). Valid range (inclusive): [10.0s, 30.0s]
        Deca110MHurdles ev = new Deca110MHurdles();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(9.999));
        // At min should be accepted
        assertDoesNotThrow(() -> ev.calculateResult(10.0));
        assertTrue(ev.calculateResult(10.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(10.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(19.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(29.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(30.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(30.001));
    }

    @Test
    @DisplayName("400m: validates bounds and exceptions")
    @Tag("400m")
    void deca400m_boundaries() throws Exception {
        // Event: 400m (track). Valid range (inclusive): [20.0s, 100.0s]
        Deca400M ev = new Deca400M();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(19.999));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(20.0));
        assertTrue(ev.calculateResult(20.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(20.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(60.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(99.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(100.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(100.001));
    }

    @Test
    @DisplayName("1500m: validates bounds and exceptions")
    @Tag("1500m")
    void deca1500m_boundaries() throws Exception {
        // Event: 1500m (track). Valid range (inclusive): [150.0s, 400.0s]
        Deca1500M ev = new Deca1500M();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(149.999));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(150.0));
        assertTrue(ev.calculateResult(150.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(150.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(300.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(399.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(400.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(400.001));
    }

    @Test
    @DisplayName("Discus Throw: validates bounds and exceptions")
    @Tag("DiscusThrow")
    void decaDiscusThrow_boundaries() throws Exception {
        // Event: Discus Throw (field). Valid range (inclusive): [0.0m, 85.0m]
        DecaDiscusThrow ev = new DecaDiscusThrow();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(20.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(84.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(85.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(85.001));
    }

    @Test
    @DisplayName("High Jump: validates bounds and exceptions")
    @Tag("HighJump")
    void decaHighJump_boundaries() throws Exception {
        // Event: High Jump (field). Units: centimeters. Valid range (inclusive): [0.0cm, 300.0cm]
        DecaHighJump ev = new DecaHighJump();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(200.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(299.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(300.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(300.001));
    }

    @Test
    @DisplayName("Javelin Throw: validates bounds and exceptions")
    @Tag("JavelinThrow")
    void decaJavelinThrow_boundaries() throws Exception {
        // Event: Javelin Throw (field). Valid range (inclusive): [0.0m, 110.0m]
        DecaJavelinThrow ev = new DecaJavelinThrow();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(60.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(109.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(110.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(110.001));
    }

    @Test
    @DisplayName("Long Jump: validates bounds and exceptions")
    @Tag("LongJump")
    void decaLongJump_boundaries() throws Exception {
        // Event: Long Jump (field). Units: centimeters. Valid range (inclusive): [0.0cm, 1000.0cm]
        DecaLongJump ev = new DecaLongJump();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(600.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(999.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(1000.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(1000.001));
    }

    @Test
    @DisplayName("Pole Vault: validates bounds and exceptions")
    @Tag("PoleVault")
    void decaPoleVault_boundaries() throws Exception {
        // Event: Pole Vault (field). Units: centimeters. Valid range (inclusive): [0.0cm, 1000.0cm]
        DecaPoleVault ev = new DecaPoleVault();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(500.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(999.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(1000.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(1000.001));
    }

    @Test
    @DisplayName("Shot Put: validates bounds and exceptions")
    @Tag("ShotPut")
    void decaShotPut_boundaries() throws Exception {
        // Event: Shot Put (field). Valid range (inclusive): [0.0m, 30.0m]
        DecaShotPut ev = new DecaShotPut();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(15.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(29.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(30.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(30.001));
    }
}
