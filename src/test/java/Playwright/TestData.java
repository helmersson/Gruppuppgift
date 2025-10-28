package Playwright;

public class TestData {
    // Competitors used across Playwright utilities
    public static final String[] COMPETITORS = new String[]{"Anna", "Berit", "Carl", "StaffanPaffan"};

    // All Decathlon events as they appear in the app (values must match the select 'value')
    public static final String[] EVENTS = new String[]{
            "100m",
            "longJump",
            "shotPut",
            "highJump",
            "400m",
            "110mHurdles",
            "discus",
            "poleVault",
            "javelin",
            "1500m"
    };

    // Returns a valid sample value string within the allowed range in app.js for each event
    public static String sampleValueForEvent(String event, String nameSeed) {
        // Slightly vary by competitor using name hash to avoid identical values
        int offset = Math.abs(nameSeed.hashCode() % 3); // 0..2
        switch (event) {
            case "100m": return new String[]{"11.2", "10.8", "12.0"}[offset];          // range 5-20
            case "110mHurdles": return new String[]{"15.3", "14.9", "16.1"}[offset];   // range 10-30
            case "400m": return new String[]{"52.4", "49.8", "58.2"}[offset];         // range 20-100
            case "1500m": return new String[]{"260", "240", "300"}[offset];           // seconds, range 150-400
            case "discus": return new String[]{"45.2", "50.1", "40.5"}[offset];       // meters, range 0-85
            case "highJump": return new String[]{"190", "175", "205"}[offset];        // cm, range 0-300
            case "javelin": return new String[]{"60.4", "55.3", "65.7"}[offset];      // meters, range 0-110
            case "longJump": return new String[]{"680", "620", "710"}[offset];        // cm, range 0-1000
            case "poleVault": return new String[]{"480", "510", "450"}[offset];       // cm, range 0-1000
            case "shotPut": return new String[]{"14.5", "13.2", "15.8"}[offset];      // meters, range 0-30
            default: return "0"; // should not happen
        }
    }
}
