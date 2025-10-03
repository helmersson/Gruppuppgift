package com.example.decathlon;

import java.util.Map;

public class PointsEngine {
    private static class Params {
        final double A, B, C;
        final boolean isTrack;
        Params(double A, double B, double C, boolean isTrack) {
            this.A = A; this.B = B; this.C = C; this.isTrack = isTrack;
        }
    }

    private static final Map<String, Params> EVENTS = Map.ofEntries(
            // Decathlon
            Map.entry("100m",      new Params(25.4347, 18, 1.81, true)),
            Map.entry("LongJump",  new Params(0.14354, 220, 1.4, false)),
            Map.entry("ShotPut",   new Params(51.39, 1.5, 1.05, false)),
            Map.entry("HighJump",  new Params(0.8465, 75, 1.42, false)),
            Map.entry("400m",      new Params(1.53775, 82, 1.81, true)),
            Map.entry("110mH",     new Params(5.74352, 28.5, 1.92, true)),
            Map.entry("Discus",    new Params(12.91, 4, 1.1, false)),
            Map.entry("PoleVault", new Params(0.2797, 100, 1.35, false)),
            Map.entry("Javelin",   new Params(10.14, 7, 1.08, false)),
            Map.entry("1500m",     new Params(0.03768, 480, 1.85, true)),

            // Heptathlon
            Map.entry("200m",      new Params(4.99087, 42.5, 1.81, true)),
            Map.entry("800m",      new Params(0.11193, 254, 1.88, true)),
            Map.entry("100mH",     new Params(9.23076, 26.7, 1.835, true)),
            Map.entry("HighJumpH", new Params(1.84523, 75, 1.348, false)),
            Map.entry("LongJumpH", new Params(0.188807, 210, 1.41, false)),
            Map.entry("ShotPutH",  new Params(56.0211, 1.5, 1.05, false)),
            Map.entry("JavelinH",  new Params(15.9803, 3.8, 1.04, false))
    );

    public int calculate(String event, double value) {
        if (value <= 0) throw new IllegalArgumentException("Value must be positive");
        Params p = EVENTS.get(event);
        if (p == null) throw new IllegalArgumentException("Unknown event: " + event);
        double result = p.isTrack
                ? p.A * Math.pow(p.B - value, p.C)
                : p.A * Math.pow(value - p.B, p.C);
        return (int) Math.floor(result);
    }
}
