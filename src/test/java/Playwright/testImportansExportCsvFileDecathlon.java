package Playwright;


import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class testImportansExportCsvFileDecathlon {

    public static void main(String[] args) throws Exception {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(
                     new BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome"));
             Page page = browser.newPage()) {


            page.navigate("http://localhost:8080");
            PlaywrightAssertions.assertThat(page).hasTitle("Decathlon Web MVP");


            List<Map<String, String>> competitors = Arrays.asList(
                    Map.of("name", "Anna", "event", "100m (s)", "result", "12.34"),
                    Map.of("name", "Marre", "event", "400m", "result", "59.21"),
                    Map.of("name", "Lisa", "event", "Long Jump (cm)", "result", "485")
            );


            for (Map<String, String> competitor : competitors) {
                String name = competitor.get("name");
                String event = competitor.get("event");
                String result = competitor.get("result");

                page.getByPlaceholder("e.g., Anna").fill(name);
                page.getByTestId("addCompetitorBtn").click();

                page.getByPlaceholder("same as above").fill(name);
                page.getByLabel("Event").selectOption(event);
                page.locator("input").nth(2).fill(result);
                page.getByText("Save score").click();

                page.waitForTimeout(500);
            }

            Locator standings = page.locator("table");
            standings.waitFor();
            System.out.println(" Nuvarande Standings:\n" + standings.innerText());

            Download download = page.waitForDownload(() -> {
                page.click("#export");
            });
            Path exportPath = download.path();
            System.out.println("Export-fil sparad: " + exportPath);

            page.setInputFiles("#importFile", exportPath);
            page.click("#importBtn");
            System.out.println("CSV importerad!");

            page.waitForTimeout(2000);
            System.out.println("Efter import:\n" + standings.innerText());

            System.out.println(" Test klart!");
            page.waitForTimeout(3000);
        }
    }
    }

