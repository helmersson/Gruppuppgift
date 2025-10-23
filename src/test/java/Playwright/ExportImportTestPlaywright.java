package Playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

public class ExportImportTestPlaywright {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("http://localhost:8080");

            // Verify we're on a page with a title (sanity check)
            String title = page.title();
            PlaywrightAssertions.assertThat(page).hasTitle(title);


            // Add competitors once
            for (String name : TestData.COMPETITORS) {
                addCompetitor(page, name);
            }

            // Provide a simple result generator within valid ranges per event
            for (String name : TestData.COMPETITORS) {
                for (int i = 0; i < TestData.EVENTS.length; i++) {
                    String event = TestData.EVENTS[i];
                    String raw = TestData.sampleValueForEvent(event, name);
                    addScore(page, name, event, raw);
                }
            }

            // Small wait to observe results in UI when running non-headless
            page.waitForTimeout(3000);
        }
    }

    private static void addCompetitor(Page page, String name) {
        page.locator("#name").fill(name);
        page.click("#add");
    }

    private static void addScore(Page page, String name, String event, String raw) {
        page.locator("#name2").fill(name);
        page.locator("#event").selectOption(event);
        page.locator("#raw").fill(raw);
        page.click("#save");
        // brief pause to let UI update between submissions
        page.waitForTimeout(200);
    }

}
