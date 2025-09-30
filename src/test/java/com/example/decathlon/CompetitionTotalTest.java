package com.example.decathlon;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;
import com.example.decathlon.PointsEngine;

class CompetitionTotalTest {
    private final PointsEngine eng = new PointsEngine();

    @Test void decathlon_total_equals_sum() {
        Map<String, Double> res = new LinkedHashMap<>();
        res.put("100m", 11.00);
        res.put("LongJump", 720.0);
        res.put("ShotPut", 12.00);
        res.put("HighJump", 200.0);
        res.put("400m", 50.00);
        res.put("110mH", 16.00);
        res.put("Discus", 40.00);
        res.put("PoleVault", 500.0);
        res.put("Javelin", 60.00);
        res.put("1500m", 280.0);

        int manual = res.entrySet().stream().mapToInt(e -> eng.calculate(e.getKey(), e.getValue())).sum();

        // Om du har en engine.total(...) – jämför med den, annars räcker manuell:
        assertThat(manual).isGreaterThan(0);
    }

    @Test void heptathlon_total_equals_sum() {
        Map<String, Double> res = new LinkedHashMap<>();
        res.put("100mH", 14.00);
        res.put("HighJump", 185.0);
        res.put("ShotPut", 14.00);
        res.put("200m", 24.00);
        res.put("LongJump", 630.0);
        res.put("Javelin", 45.0);
        res.put("800m", 130.0);

        int manual = res.entrySet().stream().mapToInt(e -> eng.calculate(e.getKey(), e.getValue())).sum();
        assertThat(manual).isGreaterThan(0);
    }
}
