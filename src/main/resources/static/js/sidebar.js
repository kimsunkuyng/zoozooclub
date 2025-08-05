document.addEventListener('DOMContentLoaded', () => {
    // 메뉴 펼치기/접기 - 독립적으로 동작하게 함
    const menuTitles = document.querySelectorAll('.sidebarMenu h4');

    menuTitles.forEach((title) => {
        title.addEventListener('click', () => {
            const submenu = title.nextElementSibling;

            if (submenu) {
                // 현재 상태에 따라 toggle만 함 (다른 메뉴는 유지)
                const isOpen = submenu.style.display === 'block';
                submenu.style.display = isOpen ? 'none' : 'block';
            }
        });
    });

    // 메뉴 클릭 시 페이지 이동
    const menuItems = document.querySelectorAll('.sidebarMenu ul li');

    menuItems.forEach((item) => {
        item.addEventListener('click', () => {
            const page = item.getAttribute('data-page');
            if (page) {
                window.location.href = `/admin/${page}`;
            }
        });
    });

    // 햄버거 버튼으로 사이드바 토글
    const sidebar = document.querySelector('.sidebar');
    const toggleBtn = document.getElementById('sidebarToggle');

    if (sidebar && toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            sidebar.classList.toggle('hidden');
        });
    }
});
