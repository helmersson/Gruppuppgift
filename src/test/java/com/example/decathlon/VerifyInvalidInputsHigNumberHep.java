package com.example.decathlon;

import com.example.decathlon.deca.InvalidResultException;
import com.example.decathlon.heptathlon.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VerifyInvalidInputsHigNumberHep {
      @Test public void TestToLowNumberHep100MHurdels() throws IllegalArgumentException, InvalidResultException {
        Hep100MHurdles hep = new Hep100MHurdles();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(100));
        assertEquals("Value too high",exception.getMessage());
    }

    @Test
    public void TestToHighNumberHep200M() throws IllegalArgumentException, InvalidResultException {
        Hep200M hep = new Hep200M();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(55.65));
        assertEquals("Value too high",exception.getMessage());
    }
    @Test public void TestToHighNumberHep800() throws IllegalArgumentException, InvalidResultException {
        Hep800M hep = new Hep800M();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(547.25));
        assertEquals("Value too high",exception.getMessage());
    }
    @Test public void TestToLHighNumberHeptHighJump() throws IllegalArgumentException, InvalidResultException {
        HeptHightJump hep = new HeptHightJump();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(458));
        assertEquals("Value too high",exception.getMessage());
    }
    @Test public void TestToLHighNumberHeptJavelinTrow() throws IllegalArgumentException, InvalidResultException {
        HeptJavelinThrow hep = new HeptJavelinThrow();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(101));
        assertEquals("Value too high",exception.getMessage());
    }
    @Test public void TestToHighNumberHeptLongJump() throws IllegalArgumentException, InvalidResultException {
        HeptLongJump hep = new HeptLongJump();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(500));
        assertEquals("Value too high",exception.getMessage());
    }
    @Test public void TestToHighNumberHeptShotPut() throws IllegalArgumentException, InvalidResultException {
        HeptShotPut hep = new HeptShotPut();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(125.1));
        assertEquals("Value too high",exception.getMessage());
    }
}
