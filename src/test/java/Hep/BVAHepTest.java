package Hep;

import com.example.decathlon.deca.InvalidResultException;
import com.example.decathlon.heptathlon.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Heptathlon (Hep) boundary value suite")
@Tag("Hep")
@Tag("boundary")
public class BVAHepTest {
    @Test
    @DisplayName("100m Hurdles: validates bounds and exceptions")
    @Tag("100mHurdles")
    void Hep100MHurdles_boundaries() throws Exception {
        // Event: 100m Hurdles (track). Valid range (inclusive): [13.0s, 30.0s]
        Hep100MHurdles ev = new Hep100MHurdles();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(12.999));
        // At min should be accepted and non-negative
        assertDoesNotThrow(() -> ev.calculateResult(13.0));
        assertTrue(ev.calculateResult(13.0) >= 0);
        // Just above min within range
        assertTrue(ev.calculateResult(13.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(20.0) >= 0);
        // Just below max within range
        assertTrue(ev.calculateResult(29.999) >= 0);
        // At max should be accepted
        assertTrue(ev.calculateResult(30.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(30.001));
    }

    @Test
    @DisplayName("200m: validates bounds and exceptions")
    @Tag("200M")
    void hep200M_boundaries() throws Exception {
        // Event: 200m (track). Valid range (inclusive): [14.0s, 42.08s]
        Hep200M ev = new Hep200M();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(13.999));
        // At min should be accepted
        assertDoesNotThrow(() -> ev.calculateResult(14.0));
        assertTrue(ev.calculateResult(14.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(14.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(28.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(42.07) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(42.08) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(42.09));
    }

    @Test
    @DisplayName("800m: validates bounds and exceptions")
    @Tag("800m")
    void hep800m_boundaries() throws Exception {
        // Event: 800m (track). Valid range (inclusive): [70.0s, 250.79s]
        Hep800M ev = new Hep800M();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(69.999));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(70.0));
        assertTrue(ev.calculateResult(70.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(70.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(150.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(250.78) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(250.79) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(250.80));
    }

    @Test
    @DisplayName("HighJump: validates bounds and exceptions")
    @Tag("HighJump")
    void hepHighJump_boundaries() throws Exception {
        // Event: High Jump (field). Valid range (inclusive): [75.7cm, 270.0cm]
        HeptHightJump ev = new HeptHightJump();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(75.6));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(75.7));
        assertTrue(ev.calculateResult(75.7) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(75.8) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(145.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(269.0) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(270.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(271.0));
    }

    @Test
    @DisplayName("JavelinThrow: validates bounds and exceptions")
    @Tag("JavelinThrow")
    void hepJavelinThrow_boundaries() throws Exception {
        // Event: Javelin Throw (field). Valid range (inclusive): [0.0m, 100.0m]
        HeptJavelinThrow ev = new HeptJavelinThrow();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(64.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(99.0) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(100.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(100.001));
    }

    @Test
    @DisplayName("LongJump: validates bounds and exceptions")
    @Tag("LongJump")
    void hepLongJump_boundaries() throws Exception {
        // Event: Long Jump (field). Units: centimeters. Valid range (inclusive): [0.0cm, 400.0cm]
        HeptLongJump ev = new HeptLongJump();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(-0.001));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(0.0));
        assertTrue(ev.calculateResult(0.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(0.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(256.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(399.999) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(400.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(400.001));
    }

    @Test
    @DisplayName("ShotPut: validates bounds and exceptions")
    @Tag("ShotPut")
    void hepShotPut_boundaries() throws Exception {
        // Event: Shot Put (field). Valid range (inclusive): [5.0m, 100.0m]
        HeptShotPut ev = new HeptShotPut();
        // Below min -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(4.999));
        // At min accepted
        assertDoesNotThrow(() -> ev.calculateResult(5.0));
        assertTrue(ev.calculateResult(5.0) >= 0);
        // Just above min
        assertTrue(ev.calculateResult(5.001) >= 0);
        // Mid-range sanity
        assertTrue(ev.calculateResult(60.0) >= 0);
        // Just below max
        assertTrue(ev.calculateResult(99.0) >= 0);
        // At max accepted
        assertTrue(ev.calculateResult(100.0) >= 0);
        // Above max -> exception
        assertThrows(InvalidResultException.class, () -> ev.calculateResult(100.001));
    }

}
