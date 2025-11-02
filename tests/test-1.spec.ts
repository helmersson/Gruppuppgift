import { test, expect } from '@playwright/test';

test('homepage loads and shows main UI', async ({ page }) => {
  // Navigate to the app root — Playwright will use baseURL from playwright.config.ts
  await page.goto('/');

  // Basic checks that the static page and key elements are present
  await expect(page.locator('#title')).toHaveText('Decathlon');
  await expect(page.locator('[data-testid="competitorNameInput"]')).toBeVisible();
  await expect(page.locator('[data-testid="addCompetitorBtn"]')).toBeEnabled();
  await expect(page.locator('[data-testid="eventSelect"]')).toBeVisible();
});