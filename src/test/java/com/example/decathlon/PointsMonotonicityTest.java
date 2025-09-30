package com.example.decathlon;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.example.decathlon.PointsEngine;

class PointsMonotonicityTest {
    private final PointsEngine eng = new PointsEngine();

    @Test void sprint_faster_is_more_points() {
        assertThat(eng.calculate("100m", 11.50)).isGreaterThan(eng.calculate("100m", 12.00));
        assertThat(eng.calculate("400m", 50.00)).isGreaterThan(eng.calculate("400m", 52.00));
    }

    @Test void hurdles_faster_is_more_points() {
        assertThat(eng.calculate("110mH", 15.50)).isGreaterThan(eng.calculate("110mH", 16.00));
        assertThat(eng.calculate("100mH", 14.00)).isGreaterThan(eng.calculate("100mH", 14.50));
    }

    @Test void jumps_longer_or_higher_is_more_points() {
        assertThat(eng.calculate("LongJump", 690)).isGreaterThan(eng.calculate("LongJump", 660));
        assertThat(eng.calculate("HighJump", 205)).isGreaterThan(eng.calculate("HighJump", 200));
        assertThat(eng.calculate("PoleVault", 520)).isGreaterThan(eng.calculate("PoleVault", 500));
    }

    @Test void throws_longer_is_more_points() {
        assertThat(eng.calculate("ShotPut", 12.00)).isGreaterThan(eng.calculate("ShotPut", 11.50));
        assertThat(eng.calculate("Discus", 45.00)).isGreaterThan(eng.calculate("Discus", 40.00));
        assertThat(eng.calculate("Javelin", 60.00)).isGreaterThan(eng.calculate("Javelin", 55.00));
    }

    @Test void middle_distance_faster_is_more_points() {
        assertThat(eng.calculate("1500m", 270.0)).isGreaterThan(eng.calculate("1500m", 280.0));
        assertThat(eng.calculate("800m", 120.0)).isGreaterThan(eng.calculate("800m", 125.0));
    }
}
