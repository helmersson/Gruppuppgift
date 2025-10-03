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

import java.util.Map;
import static java.util.Map.entry;

class HeptathlonGoldenTest {

    @FunctionalInterface
    interface HeptathlonEvent {
        int calculate(double input);
    }

    private static double toCm(double v) {
        return v < 10 ? v * 100.0 : v; // 6.30 m -> 630 cm
    }

    private static final Map<String, HeptathlonEvent> EVENTS = Map.ofEntries(
            entry("100mH",    v -> new Hep100MHurdles().calculateResult(v)),           // sek
            entry("HighJump", v -> new HeptHightJump().calculateResult(toCm(v))),      // cm
            entry("ShotPut",  v -> new HeptShotPut().calculateResult(v)),              // meter
            entry("200m",     v -> new Hep200M().calculateResult(v)),                  // sek
            entry("LongJump", v -> new HeptLongJump().calculateResult(toCm(v))),       // cm
            entry("Javelin",  v -> new HeptJavelinThrow().calculateResult(v)),         // meter
            entry("800m",     v -> new Hep800M().calculateResult(v))                   // sek
    );

    @ParameterizedTest(name = "{index} => {0} {1} -> {2}")
    @CsvFileSource(resources = "/heptathlon_golden.csv", numLinesToSkip = 1)
    void heptathlon_official_samples_match(String event, double input, int expected) {
        HeptathlonEvent evt = EVENTS.get(event);
        assertThat(evt).as("Unknown event: %s", event).isNotNull();
        int points = evt.calculate(input);
        assertThat(points).as("Event=%s, input=%s", event, input).isEqualTo(expected);
    }
}
