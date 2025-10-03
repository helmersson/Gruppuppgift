import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.assertj.core.api.Assertions.*;


import com.example.decathlon.deca.*;

import java.util.Map;
import static java.util.Map.entry;

class DecathlonGoldenTest {
    interface DecathlonEvents {
        int calculate(double input);
    }

    private static double toCm(double v) {
        return v < 10 ? v * 100.0 : v; // 6.30 m -> 630 cm
    }

    public static final Map<String, DecathlonEvents> EVENTS = Map.ofEntries(
            entry("100m",  v -> new Deca100M().calculateResult(v)),
            entry("LongJump", v -> new DecaLongJump().calculateResult(toCm(v))),
            entry("ShotPut",  v -> new DecaShotPut().calculateResult(v)),
            entry("HighJump", v -> new DecaHighJump().calculateResult(toCm(v))),
            entry("400m", v -> new Deca400M().calculateResult(v)),
            entry("110mH", v -> new Deca110MHurdles().calculateResult(v)),
            entry("Discus", v -> new DecaDiscusThrow().calculateResult(v)),
            entry("PoleVault", v -> new DecaPoleVault().calculateResult(toCm(v))),
            entry("Javelin", v -> new DecaJavelinThrow().calculateResult(v)),
            entry("1500m", v -> new Deca1500M().calculateResult(v))
    );

    @ParameterizedTest(name = "{index} => {0} {1} -> {2}")
    @CsvFileSource(resources = "/decathlon_golden.csv", numLinesToSkip = 1)
    void decathlon_official_samples_match(String event, double input, int expected) {
        DecathlonGoldenTest.DecathlonEvents evt = EVENTS.get(event);
        assertThat(evt).as("Unknown event: %s", event).isNotNull();
        int points = evt.calculate(input);
        assertThat(points).as("Event=%s, input=%s", event, input).isEqualTo(expected);
    }
}
