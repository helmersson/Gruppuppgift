/*import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.assertj.core.api.Assertions.*;
import com.example.decathlon.PointsEngine;

class DecathlonGoldenTest {
    private final PointsEngine eng = new PointsEngine();

    @ParameterizedTest(name = "{index} => {0} {1} -> {2}")
    @CsvFileSource(resources = "/decathlon_golden.csv", numLinesToSkip = 1)
    void decathlon_official_samples_match(String event, double input, int expected) {
        int points = eng.calculate(event, input);
        assertThat(points).as("Event=%s, input=%s", event, input).isEqualTo(expected);
    }
}
*/