package com.example.decathlon;

public class PointsEngine {public int calculate(String event, double value) {
    // Lägg in din poängberäkning här
    if (value <= 0) throw new IllegalArgumentException("Value must be positive");
    switch (event) {
        case "100m":
            return (int) (25.4347 * Math.pow(18 - value, 1.81));
        case "LongJump":
            return (int) (0.14354 * Math.pow(value * 100 - 220, 1.4));
        case "ShotPut":
            return (int) (51.39 * Math.pow(value - 1.5, 1.05));
        case "400m":
            return (int) (1.53775 * Math.pow(82 - value, 1.81));
        default:
            throw new IllegalArgumentException("Unknown event: " + event);
    }
}
}
