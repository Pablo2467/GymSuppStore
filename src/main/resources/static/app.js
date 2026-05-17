// ===== BÚSQUEDA EN TIEMPO REAL =====
const searchInput = document.getElementById('searchInput');
const tableRows   = document.querySelectorAll('tbody tr[data-product]');

if (searchInput) {
    searchInput.addEventListener('input', () => {
        const query = searchInput.value.toLowerCase().trim();
        let visible = 0;
        tableRows.forEach(row => {
            const match = row.textContent.toLowerCase().includes(query);
            row.style.display = match ? '' : 'none';
            if (match) visible++;
        });
        document.getElementById('emptySearch').style.display =
            visible === 0 && query ? '' : 'none';
        document.getElementById('resultCount').textContent =
            query ? `${visible} producto(s) encontrado(s)` : '';
    });
}

// ===== VALIDACIÓN DE FORMULARIO =====
const productForm = document.getElementById('productForm');
if (productForm) {
    productForm.addEventListener('submit', e => {
        const name  = document.getElementById('nameInput').value.trim();
        const price = parseFloat(document.getElementById('priceInput').value);
        const stock = parseInt(document.getElementById('stockInput').value);
        const errors = [];
        if (name.length < 2)            errors.push('El nombre debe tener al menos 2 caracteres.');
        if (isNaN(price) || price <= 0) errors.push('El precio debe ser mayor a 0.');
        if (isNaN(stock) || stock < 0)  errors.push('El stock no puede ser negativo.');
        if (errors.length > 0) {
            e.preventDefault();
            showToast(errors.join(' | '), 'error');
        }
    });
}

// ===== TOAST =====
function showToast(msg, type = 'success') {
    const toast = document.getElementById('toast');
    if (!toast) return;
    toast.innerHTML = msg;
    toast.className = `toast toast-${type} show`;
    setTimeout(() => toast.classList.remove('show'), 3500);
}

// ===== MODAL ELIMINAR =====
document.querySelectorAll('.btn-delete').forEach(btn => {
    btn.addEventListener('click', e => {
        e.preventDefault();
        document.getElementById('modalProductName').textContent =
            btn.getAttribute('data-name');
        document.getElementById('confirmDelete').setAttribute('href',
            btn.getAttribute('href'));
        document.getElementById('deleteModal').classList.add('show');
    });
});

const cancelDelete = document.getElementById('cancelDelete');
if (cancelDelete) {
    cancelDelete.addEventListener('click', () => {
        document.getElementById('deleteModal').classList.remove('show');
    });
}

const deleteModal = document.getElementById('deleteModal');
if (deleteModal) {
    deleteModal.addEventListener('click', e => {
        if (e.target === e.currentTarget)
            deleteModal.classList.remove('show');
    });
}

// ===== ESTADÍSTICAS DINÁMICAS =====
function updateStats() {
    const rows     = document.querySelectorAll('tbody tr[data-product]');
    const cats     = new Set([...rows].map(r =>
        r.getAttribute('data-category')).filter(Boolean));
    const lowStock = [...rows].filter(r =>
        parseInt(r.getAttribute('data-stock')) < 5).length;

    const statTotal    = document.getElementById('statTotal');
    const statCats     = document.getElementById('statCats');
    const statLowStock = document.getElementById('statLowStock');

    if (statTotal)    statTotal.textContent    = rows.length;
    if (statCats)     statCats.textContent     = cats.size;
    if (statLowStock) statLowStock.textContent =
        lowStock > 0 ? ` ${lowStock}` : ' OK';
}

updateStats();
// ===== MODAL LOGOUT =====
const logoutModal   = document.getElementById('logoutModal');
const cancelLogout  = document.getElementById('cancelLogout');
const confirmLogout = document.getElementById('confirmLogout');

if (cancelLogout) {
    cancelLogout.addEventListener('click', () => {
        logoutModal.classList.remove('show');
    });
}
if (confirmLogout) {
    confirmLogout.addEventListener('click', () => {
        document.getElementById('logoutForm').submit();
    });
}
if (logoutModal) {
    logoutModal.addEventListener('click', e => {
        if (e.target === logoutModal) logoutModal.classList.remove('show');
    });
}