// 하위 메뉴 토글
document.querySelectorAll('.main-menu').forEach(menu => {
    menu.addEventListener('click', function() {
        let submenu = this.nextElementSibling;
        if (submenu.style.display === 'block') {
            submenu.style.display = 'none';
        } else {
            document.querySelectorAll('.submenu').forEach(s => s.style.display = 'none');
            submenu.style.display = 'block';
        }
    });
});

// ★ 모든 페이지 숨기기 함수
function hideAllPages() {
    document.getElementById('member-list-page').style.display = 'none';
    document.getElementById('member-grade-page').style.display = 'none';
    document.getElementById('blank-page').style.display = 'none';
    document.getElementById('notice-page').style.display = 'none';
    document.getElementById('report-page').style.display = 'none';
    document.getElementById('event-page').style.display = 'none';
    document.getElementById('coupon-page').style.display = 'none';
}

// 회원조회
document.getElementById('datatable-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('member-list-page').style.display = 'block';
    if (!window.datatableInitialized) {
        $('#sampleTable').DataTable({
            language: {
                "lengthMenu": "페이지당 _MENU_개 보기",
                "search": "검색:",
                "zeroRecords": "일치하는 데이터가 없습니다",
                "info": "총 _TOTAL_명 중 _START_~_END_명 표시",
                "infoEmpty": "데이터 없음",
                "infoFiltered": "(_MAX_명에서 필터링됨)",
                "paginate": {
                    "previous": "이전",
                    "next": "다음"
                }
            }
        });
        window.datatableInitialized = true;
    }
});

// 회원조회 검색
document.getElementById('member-search').addEventListener('keyup', function() {
    let q = this.value.toLowerCase();
    let trs = document.querySelectorAll('#sampleTable tbody tr');
    trs.forEach(tr => {
        let show = false;
        tr.querySelectorAll('td').forEach(td => {
            if (td.textContent.toLowerCase().includes(q)) show = true;
        });
        tr.style.display = show ? '' : 'none';
    });
});

// 회원등급 체크박스 전체 선택/해제
document.getElementById('checkAll').addEventListener('change', function() {
    document.querySelectorAll('.grade-check').forEach(cb => cb.checked = this.checked);
});

// 회원등급관리
document.getElementById('grade-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('member-grade-page').style.display = 'block';
});

// 공지사항 등록/삭제
document.getElementById('notice-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('notice-page').style.display = 'block';
    showNoticeTable();
});

// 공지사항 검색
document.getElementById('notice-search').addEventListener('keyup', function() {
    let q = this.value.toLowerCase();
    let trs = document.querySelectorAll('#notice-table-area tbody tr');
    trs.forEach(tr => {
        let show = false;
        tr.querySelectorAll('td').forEach(td => {
            if (td.textContent.toLowerCase().includes(q)) show = true;
        });
        tr.style.display = show ? '' : 'none';
    });
});

// 새공지사항 폼 전환
document.getElementById('newNoticeBtn').addEventListener('click', function() {
    document.getElementById('notice-table-area').style.display = 'none';
    document.getElementById('notice-form-area').style.display = 'block';
});
document.getElementById('notice-list-btn').addEventListener('click', function() {
    showNoticeTable();
});

// 새공지사항, 표 전환
function showNoticeTable() {
    document.getElementById('notice-table-area').style.display = 'block';
    document.getElementById('notice-form-area').style.display = 'none';
}

// 공지사항 표 전체 체크
document.getElementById('noticeCheckAll').addEventListener('change', function() {
    document.querySelectorAll('.notice-check').forEach(cb => cb.checked = this.checked);
});

// 신고게시물 등록/삭제
document.getElementById('report-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('report-page').style.display = 'block';
    showReportFirst();
});

// 신고게시물 검색
document.getElementById('report-search').addEventListener('keyup', function() {
    let q = this.value.toLowerCase();
    let trs = document.querySelectorAll('#report-form-area tbody tr');
    trs.forEach(tr => {
        let show = false;
        tr.querySelectorAll('td').forEach(td => {
            if (td.textContent.toLowerCase().includes(q)) show = true;
        });
        tr.style.display = show ? '' : 'none';
    });
});


// 신고게시물 첫화면 - 버튼만 보임
function showReportFirst() {
    document.getElementById('report-form-area').style.display = 'none';
}

// 신고게시물 버튼 클릭시 표/폼 노출
document.getElementById('showReportBtn').addEventListener('click', function() {
    document.getElementById('report-form-area').style.display = 'block';
});

// 신고게시물 전체 체크
document.getElementById('reportCheckAll').addEventListener('change', function() {
    document.querySelectorAll('.report-check').forEach(cb => cb.checked = this.checked);
});

// 나머지 blank-link 메뉴는 모두 blank-page로 이동, 단 notice-link/report-link는 예외!
document.querySelectorAll('.blank-link').forEach(link => {
    if (link.id !== 'notice-link' && link.id !== 'report-link' && link.id !== 'event-link' && link.id !== 'coupon-link') {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllPages();
            document.getElementById('blank-page').style.display = 'block';
        });
    }
});

// 이벤트등록 클릭
document.getElementById('event-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('event-page').style.display = 'block';
});

// 쿠폰생성/발급 클릭
document.getElementById('coupon-link').addEventListener('click', function(e) {
    e.preventDefault();
    hideAllPages();
    document.getElementById('coupon-page').style.display = 'block';
});

// 공지사항 페이지네이션 (예시)
function renderNoticePagination(current, total) {
    const container = document.getElementById('notice-pagination');
    let html = '';
    for (let i = 1; i <= total; i++) {
        html += `<button class="page-btn${i === current ? ' active' : ''}" onclick="renderNoticePagination(${i},${total})">${i}</button>`;
    }
    container.innerHTML = html;
}
renderNoticePagination(1, 5);
// 신고게시물 검색 기능 (report-search input의 keyup 이벤트)
document.getElementById('report-search').addEventListener('keyup', function() {
    let q = this.value.toLowerCase();
    let trs = document.querySelectorAll('#report-table-area tbody tr');
    trs.forEach(tr => {
        let show = false;
        tr.querySelectorAll('td').forEach(td => {
            if (td.textContent.toLowerCase().includes(q)) show = true;
        });
        tr.style.display = show ? '' : 'none';
    });
});
// 신고게시물 페이지네이션 함수 (공지사항이랑 구조 동일)
function renderReportPagination(current, total) {
    const container = document.getElementById('report-pagination');
    let html = '';
    for (let i = 1; i <= total; i++) {
        html += `<button class="page-btn${i === current ? ' active' : ''}" onclick="renderReportPagination(${i},${total})">${i}</button>`;
    }
    container.innerHTML = html;
}
renderReportPagination(1, 5); // 페이지 갯수 예시

