import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('same as above').click();
  await page.getByPlaceholder('same as above').fill('Katarina');
  await page.getByTestId('eventSelect').selectOption('longJump');
  await page.getByTestId('rawInput').click();
  await page.getByTestId('rawInput').fill('600');
  await page.getByTestId('saveScoreBtn').click();
  await page.getByRole('cell', { name: '850' }).first().click();
  await page.getByRole('cell', { name: '850' }).first().click();
  await page.getByRole('cell', { name: '850' }).first().click({
    button: 'right'
  });
  await page.getByRole('cell', { name: '850' }).first().click();
  await page.getByRole('cell', { name: '850' }).first().click();
});