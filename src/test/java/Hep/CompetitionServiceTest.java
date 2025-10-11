package Hep;


import com.example.decathlon.core.CompetitionService;
import com.example.decathlon.core.ScoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class CompetitionServiceTest {

    private CompetitionService competitionService;
    private ScoringService scoringService;

    @BeforeEach
    void setUp() {
        // Mockad ScoringService som ger fasta poäng (lätt att testa)
        scoringService = new ScoringService() {
            @Override
            public int score(String eventId, double raw) {
                // returnerar eventId:s längd * 10 för att simulera olika poäng
                return eventId.length() * 10;
            }
        };
        competitionService = new CompetitionService(scoringService);
    }

    @Test
    void testTotalPointsCalculatedCorrectly() {
        // Arrange
        String player = "Alice";
        competitionService.addCompetitor(player);

        // Spelaren deltar i 3 grenar
        competitionService.score(player, "100m", 12.5);
        competitionService.score(player, "LongJump", 5.2);
        competitionService.score(player, "ShotPut", 10.1);

        // Act
        List<Map<String, Object>> standings = competitionService.standings();
        Map<String, Object> alice = standings.get(0);

        int total = (int) alice.get("total");
        Map<String, Integer> scores = (Map<String, Integer>) alice.get("scores");

        // Assert
        int expectedTotal = scores.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(expectedTotal, total, "Totalsumman ska vara lika med summan av alla grenar");

        // Testar att totalen uppdateras när en gren ändras
        competitionService.score(player, "100m", 11.9);
        List<Map<String, Object>> updated = competitionService.standings();
        int newTotal = (int) updated.get(0).get("total");

        assertNotEquals(total, newTotal, "Totalsumman ska uppdateras när poäng ändras");
    }
}
