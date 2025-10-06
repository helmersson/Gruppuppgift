import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.assertj.core.api.Assertions.*;

import com.example.decathlon.heptathlon.Hep100MHurdles;
import com.example.decathlon.heptathlon.Hep200M;
import com.example.decathlon.heptathlon.Hep800M;
import com.example.decathlon.heptathlon.HeptHightJump;     // kontrollera stavningen mot din klass
import com.example.decathlon.heptathlon.HeptJavelinThrow;
import com.example.decathlon.heptathlon.HeptLongJump;
import com.example.decathlon.heptathlon.HeptShotPut;

import java.util.HashMap;
import java.util.Map;

class HeptathlonGoldenTest {

    @FunctionalInterface
    interface HeptathlonEvent {
        int calculate(double input) throws com.example.decathlon.deca.InvalidResultException;
    }

    private static double toCm(double v) {
        return v < 10 ? v * 100.0 : v; // 6.30 m -> 630 cm
    }

    private static final Map<String, HeptathlonEvent> EVENTS = new HashMap<>();
    static {
        EVENTS.put("100mH",    v -> new Hep100MHurdles().calculateResult(v));           // sek
        EVENTS.put("HighJump", v -> new HeptHightJump().calculateResult(toCm(v)));      // cm
        EVENTS.put("ShotPut",  v -> new HeptShotPut().calculateResult(v));              // meter
        EVENTS.put("200m",     v -> new Hep200M().calculateResult(v));                  // sek
        EVENTS.put("LongJump", v -> new HeptLongJump().calculateResult(toCm(v)));       // cm
        EVENTS.put("Javelin",  v -> new HeptJavelinThrow().calculateResult(v));         // meter
        EVENTS.put("800m",     v -> new Hep800M().calculateResult(v));                   // sek
    }

    @ParameterizedTest(name = "{index} => {0} {1} -> {2}")
    @CsvFileSource(resources = "/heptathlon_golden.csv", numLinesToSkip = 1)
    void heptathlon_official_samples_match(String event, double input, int expected) {
        HeptathlonEvent evt = EVENTS.get(event);
        assertThat(evt).as("Unknown event: %s", event).isNotNull();
        int points;
        try {
            points = evt.calculate(input);
        } catch (com.example.decathlon.deca.InvalidResultException e) {
            fail(String.format("Event=%s, input=%s threw %s: %s", event, input, e.getClass().getSimpleName(), e.getMessage()));
            return;
        }
        assertThat(points).as("Event=%s, input=%s", event, input).isEqualTo(expected);
    }
}
