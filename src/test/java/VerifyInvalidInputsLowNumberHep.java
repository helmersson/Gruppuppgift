import com.example.decathlon.deca.InvalidResultException;
import com.example.decathlon.heptathlon.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VerifyInvalidInputsLowNumberHep {

    @Test public void TestToLowNumberHep100MHurdels() throws IllegalArgumentException, InvalidResultException {
        Hep100MHurdles hep = new Hep100MHurdles();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(1.0));
        assertEquals("Value too low",exception.getMessage());
    }

    @Test public void TestToLowNumberHep200M() throws IllegalArgumentException, InvalidResultException {
        Hep200M hep = new Hep200M();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(1.0));
                assertEquals("Value too low",exception.getMessage());
    }

    @Test public void TestToLowNumberHep800() throws IllegalArgumentException, InvalidResultException {
        Hep800M hep = new Hep800M();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(10));
        assertEquals("Value too low",exception.getMessage());
    }
    @Test public void TestToLowNumberHeptHighJump() throws IllegalArgumentException, InvalidResultException {
        HeptHightJump hep = new HeptHightJump();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(45));
        assertEquals("Value too low",exception.getMessage());
    }
    @Test public void TestToLowNumberHeptJavelinTrow() throws IllegalArgumentException, InvalidResultException {
        HeptJavelinThrow hep = new HeptJavelinThrow();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(-14));
        assertEquals("Value too low",exception.getMessage());
    }
    @Test public void TestToLowNumberHeptLongJump() throws IllegalArgumentException, InvalidResultException {
        HeptLongJump hep = new HeptLongJump();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(-2));
        assertEquals("Value too low",exception.getMessage());
    }
    @Test public void TestToLowNumberHeptShotPut() throws IllegalArgumentException, InvalidResultException {
        HeptShotPut hep = new HeptShotPut();
        InvalidResultException exception = assertThrows(
                InvalidResultException.class,
                () -> hep.calculateResult(3.0));
        assertEquals("Value too low",exception.getMessage());
    }



}
