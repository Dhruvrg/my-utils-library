async function loadNamespaces() {
    const res = await fetch('/api/namespaces');
    const namespaces = await res.json();
    const container = document.getElementById('namespaces');
    container.innerHTML = '<h2>Namespaces</h2>';

    if (namespaces.length === 0) {
        container.innerHTML += "<p class='no-records'>No namespaces found.</p>";
        return;
    }

    namespaces.forEach((ns, index) => {
        const btn = document.createElement('button');
        btn.textContent = ns.name;
        btn.onclick = () => loadSets(ns.name);
        container.appendChild(btn);

        if (index === 0) {
            loadSets(ns.name, true); // auto-load first namespace & set
        }
    });
}

async function loadSets(namespace, autoLoadFirstSet = false) {
    const res = await fetch(`/api/sets/${namespace}`);
    const sets = await res.json();
    const container = document.getElementById('sets');
    container.innerHTML = `<h2>Sets in ${namespace}</h2>`;

    if (sets.length === 0) {
        container.innerHTML += "<p class='no-records'>No sets found.</p>";
        return;
    }

    sets.forEach((s, index) => {
        const btn = document.createElement('button');
        btn.textContent = s.name;
        btn.onclick = () => loadRecords(namespace, s.name);
        container.appendChild(btn);

        if (index === 0 && autoLoadFirstSet) {
            loadRecords(namespace, s.name); // auto-load first set
        }
    });
}

async function loadRecords(namespace, set) {
    const res = await fetch(`/api/records/${namespace}/${set}`);
    const records = await res.json();
    const container = document.getElementById('records');
    container.innerHTML = `<h2>Records in ${set}</h2>`;

    if (records.length === 0) {
        container.innerHTML += `<p class="no-records">No records found.</p>`;
        return;
    }

    const table = document.createElement('table');
    const headers = Object.keys(records[0]).filter(key => key !== 'key');

    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    headers.forEach(header => {
        const th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    records.forEach(record => {
        const row = document.createElement('tr');
        headers.forEach(header => {
            const td = document.createElement('td');
            const value = record[header];

            if (typeof value === 'object' && value !== null) {
                td.innerHTML = `<div class="object-value">${formatObject(value)}</div>`;
            } else {
                td.textContent = value;
            }

            row.appendChild(td);
        });
        tbody.appendChild(row);
    });
    table.appendChild(tbody);
    container.appendChild(table);
}

function formatObject(obj) {
    return Object.entries(obj)
        .map(([k, v]) => `${k}: ${v}`)
        .join('\n');
}

// Initial load
loadNamespaces();
