const el = (id) => document.getElementById(id);
const err = el('error');
const msg = el('msg');

function setError(text) { err.textContent = text; }
function setMsg(text) { msg.textContent = text; err.textContent = ''; }

const EVENTS_DEC = [
  { key: '100m', label: '100m (s)' },
  { key: 'longJump', label: 'Long Jump (cm)' },
  { key: 'shotPut', label: 'Shot Put (m)' },
  { key: 'highJump', label: 'High Jump (cm)' },
  { key: '400m', label: '400m (s)' },
  { key: '110mHurdles', label: '110m Hurdles (s)' },
  { key: 'discus', label: 'Discus (m)' },
  { key: 'poleVault', label: 'Pole Vault (cm)' },
  { key: 'javelin', label: 'Javelin (m)' },
  { key: '1500m', label: '1500m (s)' }
];

const EVENTS_HEP = [
  { key: '100mHurdles', label: '100m Hurdles (s)' },
  { key: 'highJump', label: 'High Jump (cm)' },
  { key: 'shotPut', label: 'Shot Put (m)' },
  { key: '200m', label: '200m (s)' },
  { key: 'longJump', label: 'Long Jump (cm)' },
  { key: 'javelin', label: 'Javelin (m)' },
  { key: '800m', label: '800m (s)' }
];

const RANGES_DEC = {
  '100m': [5, 20],
  '110mHurdles': [10, 30],
  '400m': [20, 100],
  '1500m': [150, 400],
  'discus': [0, 85],
  'highJump': [0, 300],
  'javelin': [0, 110],
  'longJump': [0, 1000],
  'poleVault': [0, 1000],
  'shotPut': [0, 30],
};

const RANGES_HEP = {
  '100mHurdles': [10, 30],
  '200m': [20, 100],
  '800m': [70, 250],
  'highJump': [0, 300],
  'javelin': [0, 110],
  'longJump': [0, 1000],
  'shotPut': [0, 30],
};


function currentEvents() {
  return el('mode').value === 'HEP' ? EVENTS_HEP : EVENTS_DEC;
}
function currentRanges() {
  return el('mode').value === 'HEP' ? RANGES_HEP : RANGES_DEC;
}

function populateEventSelect() {
  const s = el('event');
  const evs = currentEvents();
  s.innerHTML = evs.map(e => `<option value="${e.key}">${e.label}</option>`).join('');
}

function renderHeader() {
  const evs = currentEvents();
  const cells = ['<th>Name</th>'].concat(evs.map(e => `<th>${e.label.split(' (')[0]}</th>`)).concat('<th>Total</th>');
  el('thead').innerHTML = cells.join('');
}

function setTitle() {
  el('title').textContent = el('mode').value === 'HEP' ? 'Heptathlon' : 'Decathlon';
}

el('mode').addEventListener('change', async () => {
  await fetch('/api/clear', { method: 'POST' });
  el('name').value = '';
  el('name2').value = '';
  el('raw').value = '';
  setMsg('');
  setError('');
  populateEventSelect();
  renderHeader();
  setTitle();
  await renderStandings();
});

el('add').addEventListener('click', async () => {
  const name = el('name').value.trim();
  const mode = el('mode').value;
  try {
    const res = await fetch('/api/competitors', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, mode })
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Failed to add competitor');
    } else {
      setMsg('Added');
      el('name').value = '';
    }
    await renderStandings();
  } catch {
    setError('Network error');
  }
});

function parseDecimal(input) {
  if (typeof input !== 'string') return NaN;
  const s = input.replace(',', '.').trim();
  if (!/^[-+]?\d+([.]\d+)?$/.test(s)) return NaN;
  return parseFloat(s);
}

el('raw').addEventListener('input', () => {
  const v = el('raw').value.replace(',', '.').replace(/[^\d.+-]/g, '');
  el('raw').value = v;
});

el('save').addEventListener('click', async () => {
  const name = el('name2').value.trim();
  const eventId = el('event').value;
  const rawVal = parseDecimal(String(el('raw').value));
  if (!name) { setError('Missing name'); return; }
  if (Number.isNaN(rawVal)) { setError('Invalid number'); return; }
  const [min,max] = currentRanges()[eventId] || [Number.NEGATIVE_INFINITY, Number.POSITIVE_INFINITY];
  if (rawVal < min || rawVal > max) { setError(`Out of range (${min}–${max})`); return; }
  try {
    const res = await fetch('/api/score', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, event: eventId, raw: rawVal })
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Score failed');
      return;
    }
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    el('name2').value = '';
    el('raw').value = '';
    await renderStandings();
  } catch {
    setError('Score failed');
  }
});

let sortBroken = false;

el('export').addEventListener('click', async () => {
  try {
    const res = await fetch('/api/export.csv');
    const text = await res.text();
    const blob = new Blob([text], { type: 'text/csv;charset=utf-8' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'results.csv';
    a.click();
    sortBroken = true;
  } catch {
    setError('Export failed');
  }
});

el('importBtn').addEventListener('click', () => el('importFile').click());
el('importFile').addEventListener('change', async (e) => {
  const file = e.target.files?.[0];
  if (!file) return;
  const text = await file.text();
  try {
    const res = await fetch('/api/import.csv', {
      method: 'POST',
      headers: { 'Content-Type': 'text/plain' },
      body: text
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Import failed');
      return;
    }
    setMsg('Import done');
    await renderStandings();
  } catch {
    setError('Import failed');
  } finally {
    e.target.value = '';
  }
});

async function renderStandings() {
  try {
    const res = await fetch('/api/standings');
    if (!res.ok) throw new Error();
    const data = await res.json();
    const evs = currentEvents();
    const rows = (sortBroken ? data : data.sort((a,b)=> (b.total||0)-(a.total||0)))
      .map((r,i) => {
        const cols = [`<td>${escapeHtml(r.name)}</td>`];
        for (const e of evs) cols.push(`<td>${r.scores?.[e.key] ?? ''}</td>`);
        cols.push(`<td>${r.total ?? 0}</td>`);
        return `<tr>${cols.join('')}</tr>`;
      }).join('');
    el('standings').innerHTML = rows;
  } catch {
    setError('Could not load standings');
  }
}

function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

populateEventSelect();
renderHeader();
setTitle();
renderStandings();
