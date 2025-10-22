package com.example.decathlon.core;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ScoringService {
    public enum Type { TRACK, FIELD }
    public record EventDef(Type type, double A, double B, double C, double min, double max) {}

    private final Map<String, EventDef> DEC =
            Map.ofEntries(
                    Map.entry("100m", new EventDef(Type.TRACK, 25.4347, 18.0, 1.81, 5, 20)),
                    Map.entry("longJump", new EventDef(Type.FIELD, 0.14354, 220.0, 1.4, 0, 1000)),
                    Map.entry("shotPut", new EventDef(Type.FIELD, 51.39, 1.5, 1.05, 0, 30)),
                    Map.entry("highJump", new EventDef(Type.FIELD, 0.8465, 75.0, 1.42, 0, 300)),
                    Map.entry("400m", new EventDef(Type.TRACK, 1.53775, 82.0, 1.81, 20, 100)),
                    Map.entry("110mHurdles", new EventDef(Type.TRACK, 5.74352, 28.5, 1.92, 10, 30)),
                    Map.entry("discus", new EventDef(Type.FIELD, 12.91, 4.0, 1.1, 0, 85)),
                    Map.entry("poleVault", new EventDef(Type.FIELD, 0.2797, 100.0, 1.35, 0, 1000)),
                    Map.entry("javelin", new EventDef(Type.FIELD, 10.14, 7.0, 1.08, 0, 110)),
                    Map.entry("1500m", new EventDef(Type.TRACK, 0.03768, 480.0, 1.85, 150, 400))
            );


    private final Map<String, EventDef> HEP =
            Map.ofEntries(
                    Map.entry("100mHurdles", new EventDef(Type.TRACK, 9.23076, 26.7, 1.835, 10, 30)),
                    Map.entry("highJump", new EventDef(Type.FIELD, 1.84523, 75.0, 1.348, 0, 300)),
                    Map.entry("shotPut", new EventDef(Type.FIELD, 56.0211, 1.5, 1.05, 0, 30)),
                    Map.entry("200m", new EventDef(Type.TRACK, 4.99087, 42.5, 1.81, 20, 100)),
                    Map.entry("longJump", new EventDef(Type.FIELD, 0.188807, 210.0, 1.41, 0, 1000)),
                    Map.entry("javelin", new EventDef(Type.FIELD, 15.9803, 3.8, 1.04, 0, 110)),
                    Map.entry("800m", new EventDef(Type.TRACK, 0.11193, 254.0, 1.88, 70, 250))
            );


    public boolean validateRaw(String mode, String eventId, double raw) {
        EventDef e = ("HEP".equals(mode) ? HEP : DEC).get(eventId);
        if (e == null) return false;
        return raw >= e.min && raw <= e.max;
    }

    public int score(String mode, String eventId, double raw) {
        Map<String, EventDef> table = "HEP".equals(mode) ? HEP : DEC;
        EventDef e = table.get(eventId);
        if (e == null) return 0;
        double v;
        if (e.type == Type.TRACK) {
            double x = e.B - raw;
            if (x <= 0) return 0;
            v = e.A * Math.pow(x, e.C);
        } else {
            double x = raw - e.B;
            if (x <= 0) return 0;
            v = e.A * Math.pow(x, e.C);
        }
        return (int)Math.floor(v);
    }
}
