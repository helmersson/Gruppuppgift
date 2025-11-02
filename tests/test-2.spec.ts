import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByLabel('Competition').selectOption('HEP');
  await page.getByTestId('competitorNameInput').click();
  await page.getByTestId('competitorNameInput').fill('Katarina');
  await page.getByTestId('addCompetitorBtn').click();
  await page.getByPlaceholder('same as above').click();
  await page.getByPlaceholder('same as above').fill('katarina');
  await page.getByTestId('eventSelect').selectOption('highJump');
  await page.getByTestId('rawInput').click();
  await page.getByTestId('rawInput').fill('300');
  await page.getByTestId('saveScoreBtn').click();
  const downloadPromise = page.waitForEvent('download');
  await page.getByTestId('exportCsvBtn').click();
  const download = await downloadPromise;


  await page.getByRole('heading', { name: 'Enter result' }).click();
});