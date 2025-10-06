import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.assertj.core.api.Assertions.*;


import com.example.decathlon.deca.*;

import java.util.HashMap;
import java.util.Map;

class DecathlonGoldenTest {
    @FunctionalInterface
    interface DecathlonEvents {
        int calculate(double input) throws com.example.decathlon.deca.InvalidResultException;
    }

    private static double toCm(double v) {
        return v < 10 ? v * 100.0 : v; // 6.30 m -> 630 cm
    }

    public static final Map<String, DecathlonEvents> EVENTS = new HashMap<>();
    static {
        EVENTS.put("100m", v -> new Deca100M().calculateResult(v));
        EVENTS.put("LongJump", v -> new DecaLongJump().calculateResult(toCm(v)));
        EVENTS.put("ShotPut", v -> new DecaShotPut().calculateResult(v));
        EVENTS.put("HighJump", v -> new DecaHighJump().calculateResult(toCm(v)));
        EVENTS.put("400m", v -> new Deca400M().calculateResult(v));
        EVENTS.put("110mH", v -> new Deca110MHurdles().calculateResult(v));
        EVENTS.put("Discus", v -> new DecaDiscusThrow().calculateResult(v));
        EVENTS.put("PoleVault", v -> new DecaPoleVault().calculateResult(toCm(v)));
        EVENTS.put("Javelin", v -> new DecaJavelinThrow().calculateResult(v));
        EVENTS.put("1500m", v -> new Deca1500M().calculateResult(v));
    }

    @ParameterizedTest(name = "{index} => {0} {1} -> {2}")
    @CsvFileSource(resources = "/decathlon_golden.csv", numLinesToSkip = 1)
    void decathlon_official_samples_match(String event, double input, int expected) {
        DecathlonGoldenTest.DecathlonEvents evt = EVENTS.get(event);
        assertThat(evt).as("Unknown event: %s", event).isNotNull();
        int points;
        try {
            points = evt.calculate(input);
        } catch (com.example.decathlon.deca.InvalidResultException e) {
            fail(String.format("Event=%s, input=%s threw %s: %s", event, input, e.getClass().getSimpleName(), e.getMessage()));
            return; // unreachable, but keeps compiler happy
        }
        assertThat(points).as("Event=%s, input=%s", event, input).isEqualTo(expected);
    }
}
