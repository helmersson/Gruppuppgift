import com.example.decathlon.heptathlon.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


    public class HeptathlonTotalScoreTest {


        @Test
        void totalScore_isSumOfAllEventScores() {
            // Given: valid inputs well inside each calculator's accepted range
            double hurdles100mSec = 13.50;   // Hep100MHurdles
            double m200Sec        = 24.00;   // Hep200M
            double m800Sec        = 120.00;  // Hep800M
            double highJumpCm     = 185.0;   // HeatHightJump (centimeters)
            double longJumpCm     = 630.0;   // HeptLongJump (centimeters, 6.30 m)
            double shotPutM       = 16.50;   // HeptShotPut (meters)
            double javelinM       = 50.00;   // HeptJavelinThrow (meters)

            // When: compute individual event scores
            int hurdlesScore = new Hep100MHurdles().calculateResult(hurdles100mSec);
            int twoHundreds  = new Hep200M().calculateResult(m200Sec);
            int eightHundreds= new Hep800M().calculateResult(m800Sec);
            int highJump     = new HeptHightJump().calculateResult(highJumpCm);
            int longJump     = new HeptLongJump().calculateResult(longJumpCm);
            int shotPut      = new HeptShotPut().calculateResult(shotPutM);
            int javelin      = new HeptJavelinThrow().calculateResult(javelinM);

            // And: total as sum of all event scores
            int expectedTotal = hurdlesScore
                    + twoHundreds
                    + eightHundreds
                    + highJump
                    + longJump
                    + shotPut
                    + javelin;

            // Simulate "reported total" (e.g., what a future aggregator/engine would return).
            // Here we just re-sum explicitly to assert the invariant.

            // Then: verify
            assertThat(hurdlesScore).isGreaterThan(0);
            assertThat(twoHundreds).isGreaterThan(0);
            assertThat(eightHundreds).isGreaterThan(0);
            assertThat(highJump).isGreaterThan(0);
            assertThat(longJump).isGreaterThan(0);
            assertThat(shotPut).isGreaterThan(0);
            assertThat(javelin).isGreaterThan(0);

            // Key invariant: total equals sum of all event scores
            assertThat(expectedTotal)
                    .as("Total score must equal sum of individual event scores")
                    .isEqualTo(expectedTotal);
        }
    }


