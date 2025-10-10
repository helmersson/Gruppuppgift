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

function currentEvents() {
  return el('mode').value === 'HEP' ? EVENTS_HEP : EVENTS_DEC;
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

el('mode').addEventListener('change', async () => {
  populateEventSelect();
  renderHeader();
  await renderStandings();
});

el('add').addEventListener('click', async () => {
  const name = el('name').value;
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
    }
    await renderStandings();
  } catch (e) {
    setError('Network error');
  }
});

el('save').addEventListener('click', async () => {
  const body = {
    name: el('name2').value,
    event: el('event').value,
    raw: parseFloat(el('raw').value)
  };
  try {
    const res = await fetch('/api/score', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    await renderStandings();
  } catch (e) {
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
  } catch (e) {
    setError('Export failed');
  }
});

async function renderStandings() {
  try {
    const res = await fetch('/api/standings');
    if (!res.ok) throw new Error();
    const data = await res.json();
    const evs = currentEvents();
    const rows = (sortBroken ? data : data.sort((a,b)=> (b.total||0)-(a.total||0)))
      .map(r => {
        const cols = [`<td>${escapeHtml(r.name)}</td>`];
        for (const e of evs) cols.push(`<td>${r.scores?.[e.key] ?? ''}</td>`);
        cols.push(`<td>${r.total ?? 0}</td>`);
        return `<tr>${cols.join('')}</tr>`;
      }).join('');
    el('standings').innerHTML = rows;
  } catch (e) {
    setError('Could not load standings');
  }
}

function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

populateEventSelect();
renderHeader();
renderStandings();
