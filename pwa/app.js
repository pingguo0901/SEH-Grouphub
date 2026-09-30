// ===== 数据 =====
const PAGES = [
  { key: 'home', title: '首页', icon: '🏠' },
  { key: 'hr', title: '人事', icon: '👥' },
  { key: 'finance', title: '财务', icon: '💼' },
  { key: 'legal', title: '法务', icon: '⚖️' },
  { key: 'admin', title: '行政', icon: '🏢' },
  { key: 'audit', title: '内审', icon: '🛡️' },
  { key: 'profile', title: '我', icon: '👤' }
];

const SUBSIDIARIES = [
  { nameZh: '炙巷食铺', nameEn: 'ZHI XIANG FOOD ENTERPRISE', phone: '85251403695' },
  { nameZh: '星域臻旅', nameEn: 'STELLAR ELITE ENTERPRISE', phone: '8617098925396' },
  { nameZh: '星域科技', nameEn: 'STELLAR TECH STUDIO', phone: '6581945601' }
];

const COMPANY_STATS = [
  { zh: '星域控股集团', en: 'STELLAR ELITE HOLDINGS GROUP', note: '（包括旗下子公司）', total: 128, active: 96, pending: 4, resigned: 12, resigningSoon: 2, onLeave: 8, onDuty: 6 },
  { zh: '星域臻旅', en: 'STELLAR ELITE ENTERPRISE', note: '', total: 45, active: 36, pending: 2, resigned: 4, resigningSoon: 1, onLeave: 2, onDuty: 0 },
  { zh: '炙巷食铺', en: 'ZHI XIANG FOOD ENTERPRISE', note: '', total: 32, active: 26, pending: 1, resigned: 3, resigningSoon: 0, onLeave: 1, onDuty: 1 },
  { zh: '星域科技', en: 'STELLAR TECH STUDIO', note: '', total: 18, active: 15, pending: 1, resigned: 1, resigningSoon: 0, onLeave: 1, onDuty: 0 }
];

const HR_MODULES = [
  { label: '员工名册', icon: '🪪' },
  { label: '员工档案', icon: '📁', action: 'employees' },
  { label: '考勤管理', icon: '⏱️' },
  { label: '排班管理', icon: '📅' },
  { label: '薪资管理', icon: '💰' },
  { label: '申报报表', icon: '📊' },
  { label: '人事公告', icon: '📣' },
  { label: '人事审计记录', icon: '🕐' }
];

const EMPLOYEES = [
  { id: '101', zh: '陈晓明', en: 'CHEN XIAOMING', wechat: 'chenxm_dev', subsidiary: '星域科技', position: '工程师', phone: '139-0000-0101' },
  { id: '201', zh: '郭富城', en: 'GUO FUCHENG', wechat: 'gfc_chef', subsidiary: '炙巷食铺', position: '厨师', phone: '139-0000-0201' },
  { id: '001', zh: '黄志强', en: 'HUANG ZHIQIANG', wechat: 'hzq_driver', subsidiary: '星域臻旅', position: '司机', phone: '138-0011-0001' },
  { id: '002', zh: '李娜', en: 'LI NA', wechat: 'lina_cs', subsidiary: '星域臻旅', position: '客服', phone: '138-0011-0002' },
  { id: '102', zh: '林俊杰', en: 'LIN JUNJIE', wechat: 'linjj_design', subsidiary: '星域科技', position: '设计师', phone: '139-0000-0102' },
  { id: '202', zh: '刘德华', en: 'LIU DEHUA', wechat: 'ldh_manager', subsidiary: '炙巷食铺', position: '店长', phone: '139-0000-0202' },
  { id: '301', zh: '王芳', en: 'WANG FANG', wechat: 'wangfang_acct', subsidiary: '星域控股集团', position: '会计', phone: '137-0000-0301' },
  { id: '003', zh: '吴彦祖', en: 'WU YANZU', wechat: 'wyz_driver', subsidiary: '星域臻旅', position: '司机', phone: '138-0011-0003' },
  { id: '302', zh: '杨幂', en: 'YANG MI', wechat: 'yangmi_sec', subsidiary: '星域控股集团', position: '秘书', phone: '137-0000-0302' },
  { id: '004', zh: '张伟', en: 'ZHANG WEI', wechat: 'zhangwei_driver', subsidiary: '星域臻旅', position: '司机', phone: '138-0011-0004' },
  { id: '203', zh: '赵丽颖', en: 'ZHAO LIYING', wechat: 'zly_waiter', subsidiary: '炙巷食铺', position: '服务员', phone: '139-0000-0203' },
  { id: '103', zh: '周杰伦', en: 'ZHOU JIELUN', wechat: 'zjl_director', subsidiary: '星域科技', position: '技术总监', phone: '139-0000-0103' },
  { id: '007', zh: '阿七', en: '007', wechat: 'aqi_driver', subsidiary: '星域臻旅', position: '司机', phone: '138-0011-0007' }
];

const PLACEHOLDERS = {
  finance: { title: '财务板块', subtitle: '资金与账务', icon: '💼', desc: '资金流、账务核算、预算与报表等内容将在此呈现。' },
  legal: { title: '法务板块', subtitle: '合规与风控', icon: '⚖️', desc: '合同审查、合规管理、风险防控等内容将在此呈现。' },
  admin: { title: '行政板块', subtitle: '行政与后勤', icon: '🏢', desc: '办公资产、印章证照、后勤保障等内容将在此呈现。' },
  audit: { title: '内审板块', subtitle: '审计与监督', icon: '🛡️', desc: '内部审计、流程监督、风险预警等内容将在此呈现。' },
  profile: { title: '我', subtitle: '董事长', icon: '👤', desc: '个人中心、账号与权限设置等内容将在此呈现。' }
};

// ===== 工具 =====
function avatarColor(id) {
  const palette = ['#1E4A7A', '#2E7D6B', '#8A5A2B', '#7A3E7A', '#2E90FA', '#B4442C'];
  let h = 0;
  for (let i = 0; i < id.length; i++) h = (h * 31 + id.charCodeAt(i)) >>> 0;
  return palette[h % palette.length];
}

function buildArchive(emp) {
  return {
    header: {
      archiveNo: 'A-' + emp.id,
      businessEntity: emp.subsidiary,
      department: emp.subsidiary,
      position: emp.position,
      status: '在职',
      name: emp.zh,
      nameEn: emp.en
    },
    sections: [
      { title: '个人基础资料', icon: '👤', rows: [
        ['姓名', emp.zh], ['性别', '男'], ['NRIC/Passport', '900101-' + emp.id + '-0000'], ['EMF编号', 'EMF' + emp.id],
        ['出生日期', '1990-01-01'], ['国籍', '马来西亚'], ['邮箱地址', emp.wechat + '@stellarelite.com'], ['婚姻状态', '未婚'],
        ['联系电话', emp.phone], ['居住地址', 'Kuala Lumpur, Malaysia'], ['紧急联系人姓名', '待填写'], ['紧急联系人关系', '待填写'], ['紧急联系人电话', '待填写']
      ]},
      { title: '雇佣信息', icon: '💼', rows: [
        ['基础薪资', 'RM 3,000'], ['EPF会员编号', 'EPF-' + emp.id], ['SOCSO编号', 'SOCSO-' + emp.id], ['EIS编号', 'EIS-' + emp.id],
        ['LHDN税务编号', 'LHDN-' + emp.id], ['银行名称', 'Maybank'], ['银行持有人', emp.en], ['银行账户', '1122-' + emp.id + '-8899']
      ]},
      { title: '雇佣合约信息', icon: '📄', rows: [
        ['入职日期', '2023-06-01'], ['试用期起始', '2023-06-01'], ['试用期截止', '2023-09-01'], ['合约到期', '长期'],
        ['雇佣类型', '全职'], ['工作地点', emp.subsidiary], ['直属主管', '董事长']
      ]},
      { title: '证件与附件', icon: '📎', rows: [
        ['NRIC/Passport复印件', '已提交'], ['雇佣合约', '已提交'], ['相关执照', emp.position === '司机' ? '已提交' : '不适用'],
        ['跨境资格/工作许可', '已提交'], ['健康证明', '已提交']
      ]},
      { title: '假期与考勤摘要', icon: '🗓️', rows: [
        ['年假剩余天数', '10 天'], ['病假剩余天数', '8 天'], ['最后年假日期', '2026-08-10'], ['最后病假日期', '2026-07-05'],
        ['最后事假日期', '2026-06-18'], ['系部联系电话', emp.phone], ['事假记录', '1 次'], ['迟到记录', '0 次'],
        ['旷工记录', '0 次'], ['状态记录', '正常']
      ]},
      { title: '变更记录与认证', icon: '🕐', rows: [
        ['修改日期', '2026-09-24'], ['修改操作人', '小聪'], ['变更前', '—'], ['变更后', '—']
      ]}
    ]
  };
}

// ===== 渲染 =====
function pageHeader(title, subtitle, icon) {
  return '<div class="page-header"><div class="header-row"><span class="header-icon">' + icon + '</span><h1>' + title + '</h1></div><div class="subtitle">' + subtitle + '</div></div>';
}

function renderHome() {
  let cards = SUBSIDIARIES.map(function (s) {
    const link = s.phone ? 'https://wa.me/' + s.phone : '#';
    const desc = s.phone ? 'WhatsApp 联系' : '号码待配置';
    return '<div class="sub-card" onclick="window.open(\'' + link + '\', \'_blank\')">' +
      '<div class="sub-logo">💬</div>' +
      '<div class="sub-info"><div class="sub-name">' + s.nameZh + '</div><div class="sub-desc">' + desc + '</div></div>' +
      '<div class="sub-arrow">›</div></div>';
  }).join('');
  return pageHeader('首页', '星域控股集团 · 董事长驾驶舱', '🏠') +
    '<div class="page-body">' +
    '<div class="placeholder-title">欢迎回来，董事长</div>' +
    '<div class="placeholder-desc">这里是集团全域概览，后续接入各板块核心数据。</div>' +
    '<div style="height:24px"></div>' +
    '<div class="section-title">子公司 WhatsApp</div>' +
    cards +
    '</div>';
}

function renderHr() {
  let stats = COMPANY_STATS.map(function (c) {
    return '<div class="stats-card">' +
      '<div class="stats-head"><span class="stats-zh">' + c.zh + '</span>' + (c.note ? '<span class="stats-note">' + c.note + '</span>' : '') + '</div>' +
      '<div class="stats-en">' + c.en + '</div>' +
      '<div class="stats-total-label">总员工人数</div>' +
      '<div class="stats-total">' + c.total + '</div>' +
      '<div class="stats-divider"></div>' +
      '<div class="stats-row">' +
        '<div class="stat-cell"><div class="stat-label">在职</div><div class="stat-value" style="color:#30D158">' + c.active + '</div></div>' +
        '<div class="stat-cell"><div class="stat-label">待入职</div><div class="stat-value" style="color:#0A84FF">' + c.pending + '</div></div>' +
        '<div class="stat-cell"><div class="stat-label">辞职</div><div class="stat-value" style="color:#FF453A">' + c.resigned + '</div></div>' +
      '</div>' +
      '<div class="stats-row">' +
        '<div class="stat-cell"><div class="stat-label">即将辞职</div><div class="stat-value" style="color:#FF9F0A">' + c.resigningSoon + '</div></div>' +
        '<div class="stat-cell"><div class="stat-label">放假</div><div class="stat-value" style="color:#98A6B2">' + c.onLeave + '</div></div>' +
        '<div class="stat-cell"><div class="stat-label">值班</div><div class="stat-value" style="color:#BF5AF2">' + c.onDuty + '</div></div>' +
      '</div></div>';
  }).join('');

  let modules = HR_MODULES.map(function (m) {
    const action = m.action || '';
    return '<button class="module-btn" data-action="' + action + '"><span class="icon">' + m.icon + '</span><span class="label">' + m.label + '</span></button>';
  }).join('');

  return pageHeader('人事', '组织与人才', '👥') +
    '<div class="page-body">' + stats +
    '<div style="height:8px"></div>' +
    '<div class="module-grid">' + modules + '</div>' +
    '</div>';
}

function renderPlaceholder(key) {
  const p = PLACEHOLDERS[key];
  return pageHeader(p.title, p.subtitle, p.icon) +
    '<div class="page-body"><div class="placeholder-title">' + p.title + '</div><div class="placeholder-desc">' + p.desc + '</div></div>';
}

function renderPage(key) {
  if (key === 'home') return renderHome();
  if (key === 'hr') return renderHr();
  return renderPlaceholder(key);
}

function renderNav() {
  return PAGES.map(function (p) {
    return '<button class="nav-item" data-key="' + p.key + '"><span class="icon">' + p.icon + '</span><span class="label">' + p.title + '</span></button>';
  }).join('');
}

function renderEmployeeList() {
  const sorted = EMPLOYEES.slice().sort(function (a, b) { return a.en.localeCompare(b.en); });
  const groups = {};
  sorted.forEach(function (e) {
    const c = e.en.charAt(0).toUpperCase();
    const k = (c >= 'A' && c <= 'Z') ? c : '#';
    (groups[k] = groups[k] || []).push(e);
  });
  const keys = Object.keys(groups).sort(function (a, b) { return (a === '#' ? 'ZZZZ' : a) < (b === '#' ? 'ZZZZ' : b) ? -1 : 1; });

  let html = '<div class="overlay-header"><button class="back" data-back="hr">‹</button><h2>员工档案</h2></div>' +
    '<div class="search-box"><span class="search-icon">🔍</span><input id="emp-search" type="text" placeholder="搜索ID" /><span class="clear" id="emp-clear" style="display:none">✕</span></div>' +
    '<div id="emp-list">';

  keys.forEach(function (k) {
    html += '<div class="emp-section-header">' + k + '</div>';
    groups[k].forEach(function (e) {
      html += '<div class="emp-card" data-id="' + e.id + '">' +
        '<div class="emp-avatar" style="background:' + avatarColor(e.id) + '">' + (e.zh.charAt(0) || '?') + '</div>' +
        '<div class="emp-info">' +
          '<div class="emp-name-row"><span class="emp-name">' + e.zh + '  ' + e.en + '</span><span class="emp-id">ID ' + e.id + '</span></div>' +
          '<div class="emp-line"><span>微信号 ' + e.wechat + '</span><span class="sub">' + e.subsidiary + '（' + e.position + '）</span></div>' +
          '<div class="emp-line"><span>手机号码 ' + e.phone + '</span><span class="sub">' + e.subsidiary + '（' + e.position + '）</span></div>' +
        '</div></div>';
    });
  });

  html += '</div>';
  return html;
}

function renderEmployeeArchive(emp) {
  const a = buildArchive(emp);
  let sections = a.sections.map(function (s) {
    let rows = s.rows.map(function (r) {
      return '<div class="archive-row"><span class="archive-label">' + r[0] + '</span><span class="archive-value">' + r[1] + '</span></div>';
    }).join('');
    return '<div class="archive-section"><div class="archive-section-title"><span class="icon">' + s.icon + '</span>' + s.title + '</div>' + rows + '</div>';
  }).join('');

  return '<div class="overlay-header"><button class="back" data-back="employees">‹</button><h2>员工个人档案</h2></div>' +
    '<div class="archive-body">' +
      '<div class="archive-tag-row">' +
        '<span class="archive-tag">档案编号 ' + a.header.archiveNo + '</span>' +
        '<span class="archive-tag">' + a.header.businessEntity + '</span>' +
        '<span class="archive-tag archive-status">' + a.header.status + '</span>' +
      '</div>' +
      '<div class="archive-name">' + a.header.name + '</div>' +
      '<div class="archive-sub">' + a.header.nameEn + ' · ' + a.header.department + ' · ' + a.header.position + '</div>' +
      sections +
    '</div>';
}

// ===== 导航 =====
let currentPage = 'home';
const contentEl = function () { return document.getElementById('content'); };
const overlayEl = function () { return document.getElementById('overlay'); };

function navigate(key) {
  currentPage = key;
  contentEl().innerHTML = renderPage(key);
  document.querySelectorAll('.nav-item').forEach(function (n) {
    n.classList.toggle('active', n.getAttribute('data-key') === key);
  });
  contentEl().scrollTop = 0;
}

function openEmployees() {
  overlayEl().classList.remove('hidden');
  overlayEl().innerHTML = renderEmployeeList();
  bindEmployeeList();
}

function openArchive(empId) {
  const emp = EMPLOYEES.find(function (e) { return e.id === empId; });
  if (!emp) return;
  overlayEl().innerHTML = renderEmployeeArchive(emp);
  bindBack('employees');
}

function closeOverlay() {
  overlayEl().classList.add('hidden');
  overlayEl().innerHTML = '';
}

function bindBack(target) {
  const b = overlayEl().querySelector('.back');
  if (b) b.addEventListener('click', function () {
    if (target === 'hr') { closeOverlay(); }
    else if (target === 'employees') { overlayEl().innerHTML = renderEmployeeList(); bindEmployeeList(); }
  });
}

function bindEmployeeList() {
  const input = document.getElementById('emp-search');
  const clear = document.getElementById('emp-clear');
  const list = document.getElementById('emp-list');

  function filter() {
    const q = input.value.trim();
    clear.style.display = q ? 'block' : 'none';
    document.querySelectorAll('.emp-card').forEach(function (card) {
      const id = card.getAttribute('data-id');
      card.style.display = (!q || id.indexOf(q) !== -1) ? 'flex' : 'none';
    });
    document.querySelectorAll('.emp-section-header').forEach(function (h) {
      let hasVisible = false;
      let el = h.nextElementSibling;
      while (el && !el.classList.contains('emp-section-header')) {
        if (el.classList.contains('emp-card') && el.style.display !== 'none') { hasVisible = true; break; }
        el = el.nextElementSibling;
      }
      h.style.display = hasVisible ? 'block' : 'none';
    });
  }

  input.addEventListener('input', filter);
  clear.addEventListener('click', function () { input.value = ''; filter(); });
  document.querySelectorAll('.emp-card').forEach(function (card) {
    card.addEventListener('click', function () { openArchive(card.getAttribute('data-id')); });
  });
}

// ===== 初始化 =====
function init() {
  document.getElementById('bottom-nav').innerHTML = renderNav();
  navigate('home');
  document.querySelectorAll('.nav-item').forEach(function (n) {
    n.addEventListener('click', function () { navigate(n.getAttribute('data-key')); });
  });
  document.addEventListener('click', function (e) {
    const mod = e.target.closest('.module-btn');
    if (mod && mod.getAttribute('data-action') === 'employees') openEmployees();
  });
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/sw.js').catch(function () {});
  }
}

init();
