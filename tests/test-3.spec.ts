import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByTestId('competitorNameInput').click();
  await page.getByTestId('competitorNameInput').fill('test');
  await page.getByText('Add competitor Name Add').click();
  await page.getByTestId('addCompetitorBtn').click();
  await page.getByPlaceholder('same as above').click();
  await page.getByPlaceholder('same as above').fill('test');
  await page.getByTestId('rawInput').click();
  await page.getByTestId('rawInput').fill('44');
  await page.getByTestId('saveScoreBtn').click();
  const downloadPromise1 = page.waitForEvent('download');
  await page.getByTestId('exportCsvBtn').click();
  const download1 = await downloadPromise1;
  const downloadPromise2 = page.waitForEvent('download');
  await page.getByTestId('exportCsvBtn').click();
  const download2 = await downloadPromise2;
});