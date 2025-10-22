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

    public synchronized boolean competitorExists(String name) {
        return competitors.containsKey(name);
    }

    public synchronized int score(String name, String eventId, double raw) {
        Competitor c = competitors.get(name);
        if (c == null) throw new IllegalStateException("Competitor not found");
        if (!scoring.validateRaw(c.mode, eventId, raw)) throw new IllegalArgumentException("Out of range");
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

    public synchronized void clear() {
        competitors.clear();
    }

    public synchronized void importCsv(String csv) {
        competitors.clear();
        String[] lines = csv.split("\\r?\\n");
        if (lines.length < 2) return;
        String[] header = lines[0].split(",", -1);
        List<String> cols = Arrays.asList(header);
        int nameIdx = cols.indexOf("Name");
        int modeIdx = cols.indexOf("Mode");
        int totalIdx = cols.lastIndexOf("Total");
        List<String> eventIds = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            if (i != nameIdx && i != modeIdx && i != totalIdx) eventIds.add(cols.get(i));
        }
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            String[] parts = lines[i].split(",", -1);
            if (parts.length < 2) continue;
            String name = parts[nameIdx].trim();
            String mode = parts[modeIdx].trim().toUpperCase();
            if (name.isEmpty()) continue;
            if (!mode.equals("DEC") && !mode.equals("HEP")) mode = "DEC";
            if (!competitors.containsKey(name)) competitors.put(name, new Competitor(name, mode));
            Competitor c = competitors.get(name);
            for (int j = 0, col = 0; j < parts.length; j++) {
                if (j == nameIdx || j == modeIdx || j == totalIdx) continue;
                String ev = eventIds.get(col++);
                String v = parts[j].trim();
                if (!v.isEmpty()) {
                    try {
                        int pts = Integer.parseInt(v);
                        c.points.put(ev, pts);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }
}
