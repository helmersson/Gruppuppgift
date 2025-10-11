package com.example.decathlon.core;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    private final ScoringService scoring;

    public CompetitionService(ScoringService scoring) { this.scoring = scoring; }

    public static class Competitor {
        public final String name;
        public final String mode;
        public final Map<String, Integer> points = new ConcurrentHashMap<>();
        public Competitor(String name, String mode) { this.name = name; this.mode = mode; }
        public int total() { return points.values().stream().mapToInt(i -> i).sum(); }
    }

    private static final int MAX = 40;
    private final Map<String, Competitor> competitors = new LinkedHashMap<>();

    public synchronized boolean addCompetitor(String name, String mode) {
        if (name == null || name.isBlank()) return false;
        if (!mode.equals("DEC") && !mode.equals("HEP")) return false;
        if (competitors.size() >= MAX) return false;
        if (competitors.containsKey(name)) return false;
        competitors.put(name, new Competitor(name, mode));
        return true;
    }

    public synchronized int score(String name, String eventId, double raw) {
        Competitor c = competitors.computeIfAbsent(name, n -> new Competitor(n, "DEC"));
        int pts = scoring.score(c.mode, eventId, raw);
        c.points.put(eventId, pts);
        return pts;
    }

    public synchronized List<Map<String, Object>> standings() {
        return competitors.values().stream()
                .map(c -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("name", c.name);
                    m.put("mode", c.mode);
                    m.put("scores", new LinkedHashMap<>(c.points));
                    m.put("total", c.total());
                    return m;
                })
                .sorted(Comparator.comparingInt(m -> -((Integer) m.get("total"))))
                .collect(Collectors.toList());
    }

    public synchronized String exportCsv() {
        Set<String> eventIds = new LinkedHashSet<>();
        competitors.values().forEach(c -> eventIds.addAll(c.points.keySet()));
        List<String> header = new ArrayList<>();
        header.add("Name");
        header.add("Mode");
        header.addAll(eventIds);
        header.add("Total");

        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", header)).append("\n");
        for (Competitor c : competitors.values()) {
            List<String> row = new ArrayList<>();
            row.add(c.name);
            row.add(c.mode);
            int sum = 0;
            for (String ev : eventIds) {
                Integer p = c.points.get(ev);
                row.add(p == null ? "" : String.valueOf(p));
                if (p != null) sum += p;
            }
            row.add(String.valueOf(sum));
            sb.append(String.join(",", row)).append("\n");
        }
        return sb.toString();
    }
}
