import com.example.decathlon.PointsEngine;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

// Byt till din riktiga motor/klass:
class MainGUITest {

    private PointsEngine engine; // t.ex. din implementation

    @BeforeEach
    void setUp() {
        // Byt till din konkreta klass/byggare:
        engine = new PointsEngine();
    }

    // 1) Tabellstyrda “golden” tester mot kända värden
    @ParameterizedTest(name = "{index} => {0} {1} -> {2}p")
    @CsvFileSource(resources = "/decathlon_samples.csv", numLinesToSkip = 1)
    void should_match_official_samples(String event, double input, int expected) {
        int points = engine.calculate(event, input);
        assertThat(points)
                .as("Event=%s, input=%s", event, input)
                .isEqualTo(expected);
    }

    // 2) Egenskap: sprint snabbare tid => fler poäng
    @Test
    void sprint_should_reward_faster_times() {
        int slow = engine.calculate("100m", 12.00);
        int fast = engine.calculate("100m", 11.50);
        assertThat(fast).isGreaterThan(slow);
    }

    // 3) Egenskap: hopp/kast längre => fler poäng
    @Test
    void field_events_should_reward_longer_distance() {
        int shorter = engine.calculate("LongJump", 660);
        int longer  = engine.calculate("LongJump", 690);
        assertThat(longer).isGreaterThan(shorter);
    }

    // 4) Gränsvärden/ogiltigt: 0 eller negativt ska avvisas
    @ParameterizedTest
    @CsvSource({
            "100m,0.0",
            "LongJump,-1.0",
            "ShotPut,0.0"
    })
    void invalid_input_should_throw(String event, double value) {
        assertThatThrownBy(() -> engine.calculate(event, value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // 5) Avrundning: definiera exakt regel (t.ex. närmast heltal nedåt)
    @ParameterizedTest
    @CsvSource({
            "100m,11.234,expectedDown",
            "100m,11.231,expectedDown"
    })
    void rounding_rule_should_be_consistent(String event, double time, String _unused) {
        // Byt mot faktiska förväntningar enligt din regel
        int p1 = engine.calculate(event, time);
        int p2 = engine.calculate(event, time + 0.0009);
        // Exempel: poäng får inte hoppa “upp och ner” för mikroskillnader
        assertThat(Math.abs(p1 - p2)).isLessThanOrEqualTo(1);
    }

    // 6) Summering över flera grenar
    @Test
    void total_points_for_athlete_should_sum_events() {
        int p100  = engine.calculate("100m", 11.00);
        int plong = engine.calculate("LongJump", 720);//cm
        int pshot = engine.calculate("ShotPut", 12.00);
        int p400  = engine.calculate("400m", 50.00);

        int total = p100 + plong + pshot + p400;
        assertThat(total).isGreaterThan(0);

        //  en metod engine.total(map) — testa den mot samma summa:
        // assertThat(engine.total(Map.of("100m",11.00,"LongJump",720 i cm,"ShotPut",12.00,"400m",50.00)))
        //   .isEqualTo(total);
    }

    // 7) Okända grenar ska ge tydligt fel (eller 0, enligt din policy)
    @Test
    void unknown_event_should_fail_cleanly() {
        assertThatThrownBy(() -> engine.calculate("UnknownEvent", 1.0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
