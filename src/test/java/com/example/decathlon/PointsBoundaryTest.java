package com.example.decathlon;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.example.decathlon.PointsEngine;

class PointsBoundaryTest {
    private final PointsEngine eng = new PointsEngine();

    @Test void track_when_P_ge_B_should_be_zero() {
        assertThat(eng.calculate("100m", 25.0)).isEqualTo(0); // (B - P) <= 0
    }

    @Test void field_when_P_le_B_should_be_zero() {
        assertThat(eng.calculate("LongJump", 720)).isEqualTo(0); // (P - B) <= 0 (exempelvärde)
    }
}
