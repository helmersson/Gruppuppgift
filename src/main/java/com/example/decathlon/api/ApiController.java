package com.example.decathlon.api;

import com.example.decathlon.core.CompetitionService;
import com.example.decathlon.dto.ScoreReq;
import com.example.decathlon.core.ScoringService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final CompetitionService comp;
    private final ScoringService scoring;

    public ApiController(CompetitionService comp, ScoringService scoring) {
        this.comp = comp;
        this.scoring = scoring;
    }

    @PostMapping("/competitors")
    public ResponseEntity<?> add(@RequestBody Map<String,String> body) {
        String name = Optional.ofNullable(body.get("name")).orElse("").trim();
        String mode = Optional.ofNullable(body.get("mode")).orElse("DEC").trim().toUpperCase();
        if (name.isEmpty()) return ResponseEntity.badRequest().body("Empty name");
        boolean ok = comp.addCompetitor(name, mode);
        if (!ok) return ResponseEntity.status(409).body("Cannot add competitor");
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/score")
    public ResponseEntity<?> score(@RequestBody ScoreReq r) {
        if (!comp.competitorExists(r.name())) return ResponseEntity.status(404).body("Competitor not found");
        try {
            int pts = comp.score(r.name(), r.event(), r.raw());
            return ResponseEntity.ok(Map.of("points", pts));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Out of range");
        }
    }


    @GetMapping("/standings")
    public List<Map<String,Object>> standings() { return comp.standings(); }

    @GetMapping(value="/export.csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String export() { return comp.exportCsv(); }

    @PostMapping("/clear")
    public ResponseEntity<?> clear() { comp.clear(); return ResponseEntity.ok().build(); }

    @PostMapping(value="/import.csv", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> importCsv(@RequestBody String csv) {
        comp.importCsv(csv);
        return ResponseEntity.ok().build();
    }
}
