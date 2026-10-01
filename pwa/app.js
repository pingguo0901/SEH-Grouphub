// ===== Material 矢量图标（24x24 path） =====
const ICONS = {
  home: 'M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z',
  people: 'M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z',
  wallet: 'M21 18v1c0 1.1-.9 2-2 2H5c-1.11 0-2-.9-2-2V5c0-1.1.89-2 2-2h14c1.1 0 2 .9 2 2v1h-9c-1.11 0-2 .9-2 2v8c0 1.1.89 2 2 2h9zm-9-2h10V8H12v8zm4-2.5c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5z',
  gavel: 'M1 21h12v2H1v-2zM5.24 8.07l2.83-2.83 14.14 14.14-2.83 2.83L5.24 8.07zM12.32 1l5.66 5.66-2.83 2.83-5.66-5.66L12.32 1zM3.83 9.48l5.66 5.66-2.83 2.83-5.66-5.66 2.83-2.83z',
  business: 'M12 7V3H2v18h20V7H12zM6 19H4v-2h2v2zm0-4H4v-2h2v2zm0-4H4V9h2v2zm0-4H4V5h2v2zm4 12H8v-2h2v2zm0-4H8v-2h2v2zm0-4H8V9h2v2zm0-4H8V5h2v2zm10 12h-8v-2h2v-2h-2v-2h2v-2h-2V9h8v10zm-2-8h-2v2h2v-2zm0 4h-2v2h2v-2z',
  shield: 'M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z',
  person: 'M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z',
  badge: 'M20 7h-5V4c0-1.1-.9-2-2-2h-2c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM9 12c.83 0 1.5.67 1.5 1.5S9.83 15 9 15s-1.5-.67-1.5-1.5S8.17 12 9 12zm3 6H6v-.75c0-1 2-1.5 3-1.5s3 .5 3 1.5V18zm1-9h-2V4h2v5zm5 7.5h-4V15h4v1.5zm0-3h-4V12h4v1.5z',
  folder: 'M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z',
  clock: 'M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z',
  calendar: 'M19 4h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V10h14v10zM9 14H7v-2h2v2zm4 0h-2v-2h2v2zm4 0h-2v-2h2v2zm-8 4H7v-2h2v2zm4 0h-2v-2h2v2zm4 0h-2v-2h2v2z',
  payments: 'M19 14V6c0-1.1-.9-2-2-2H3c-1.1 0-2 .9-2 2v8c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zm-9-1c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm13-6v11c0 1.1-.9 2-2 2H4v-2h17V7h2z',
  chart: 'M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z',
  campaign: 'M18 11v2h4v-2h-4zm-2 6.61c.96.71 2.21 1.65 3.2 2.39.4-.53.8-1.07 1.2-1.6-.99-.74-2.24-1.68-3.2-2.4-.4.54-.8 1.08-1.2 1.61zM20.4 5.6c-.4-.53-.8-1.07-1.2-1.6-.99.74-2.24 1.68-3.2 2.4.4.53.8 1.07 1.2 1.6.96-.72 2.21-1.65 3.2-2.4zM4 9c-1.1 0-2 .9-2 2v2c0 1.1.9 2 2 2h1v4h2v-4h1l5 3V6L8 9H4zm11.5 3c0-1.33-.58-2.53-1.5-3.35v6.69c.92-.81 1.5-2.01 1.5-3.34z',
  history: 'M13 3c-4.97 0-9 4.03-9 9H1l3.89 3.89.07.14L9 12H6c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.93 0-3.68-.79-4.94-2.06l-1.42 1.42C8.27 19.99 10.51 21 13 21c4.97 0 9-4.03 9-9s-4.03-9-9-9zm-1 5v5l4.28 2.54.72-1.21-3.5-2.08V8H12z',
  search: 'M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z',
  chat: 'M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z',
  work: 'M20 6h-4V4c0-1.11-.89-2-2-2h-4c-1.11 0-2 .89-2 2v2H4c-1.11 0-1.99.89-1.99 2L2 19c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V8c0-1.11-.89-2-2-2zm-6 0h-4V4h4v2z',
  description: 'M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2zm-3-5V3.5L18.5 9H13z',
  attach: 'M16.5 6v11.5c0 2.21-1.79 4-4 4s-4-1.79-4-4V5c0-1.38 1.12-2.5 2.5-2.5s2.5 1.12 2.5 2.5v10.5c0 .55-.45 1-1 1s-1-.45-1-1V6H10v9.5c0 1.38 1.12 2.5 2.5 2.5s2.5-1.12 2.5-2.5V5c0-2.21-1.79-4-4-4S7 2.79 7 5v12.5c0 3.04 2.46 5.5 5.5 5.5s5.5-2.46 5.5-5.5V6h-1.5z',
  event: 'M17 12h-5v5h5v-5zM16 1v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-1V1h-2zm3 18H5V8h14v11z',
  back: 'M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z',
  close: 'M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z'
};

function icon(name, cls) {
  cls = cls || 'icon';
  return '<svg class="' + cls + '" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true"><path d="' + (ICONS[name] || '') + '"/></svg>';
}

// ===== 数据 =====
const PAGES = [
  { key: 'home', title: '首页', icon: 'home' },
  { key: 'hr', title: '人事', icon: 'people' },
  { key: 'finance', title: '财务', icon: 'wallet' },
  { key: 'legal', title: '法务', icon: 'gavel' },
  { key: 'admin', title: '行政', icon: 'business' },
  { key: 'audit', title: '内审', icon: 'shield' },
  { key: 'profile', title: '我', icon: 'person' }
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
  { label: '员工名册', icon: 'badge' },
  { label: '员工档案', icon: 'folder', action: 'employees' },
  { label: '考勤管理', icon: 'clock' },
  { label: '排班管理', icon: 'calendar' },
  { label: '薪资管理', icon: 'payments' },
  { label: '申报报表', icon: 'chart' },
  { label: '人事公告', icon: 'campaign' },
  { label: '人事审计记录', icon: 'history' }
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
  finance: { title: '财务板块', subtitle: '资金与账务', icon: 'wallet', desc: '资金流、账务核算、预算与报表等内容将在此呈现。' },
  legal: { title: '法务板块', subtitle: '合规与风控', icon: 'gavel', desc: '合同审查、合规管理、风险防控等内容将在此呈现。' },
  admin: { title: '行政板块', subtitle: '行政与后勤', icon: 'business', desc: '办公资产、印章证照、后勤保障等内容将在此呈现。' },
  audit: { title: '内审板块', subtitle: '审计与监督', icon: 'shield', desc: '内部审计、流程监督、风险预警等内容将在此呈现。' },
  profile: { title: '我', subtitle: '董事长', icon: 'person', desc: '个人中心、账号与权限设置等内容将在此呈现。' }
};

// ===== 工具 =====
function avatarColor(id) {
  const palette = ['#3A3A3A', '#2E7D6B', '#8A5A2B', '#7A3E7A', '#555555', '#B4442C'];
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
      { title: '个人基础资料', icon: 'person', rows: [
        ['姓名', emp.zh], ['性别', '男'], ['NRIC/Passport', '900101-' + emp.id + '-0000'], ['EMF编号', 'EMF' + emp.id],
        ['出生日期', '1990-01-01'], ['国籍', '马来西亚'], ['邮箱地址', emp.wechat + '@stellarelite.com'], ['婚姻状态', '未婚'],
        ['联系电话', emp.phone], ['居住地址', 'Kuala Lumpur, Malaysia'], ['紧急联系人姓名', '待填写'], ['紧急联系人关系', '待填写'], ['紧急联系人电话', '待填写']
      ]},
      { title: '雇佣信息', icon: 'work', rows: [
        ['基础薪资', 'RM 3,000'], ['EPF会员编号', 'EPF-' + emp.id], ['SOCSO编号', 'SOCSO-' + emp.id], ['EIS编号', 'EIS-' + emp.id],
        ['LHDN税务编号', 'LHDN-' + emp.id], ['银行名称', 'Maybank'], ['银行持有人', emp.en], ['银行账户', '1122-' + emp.id + '-8899']
      ]},
      { title: '雇佣合约信息', icon: 'description', rows: [
        ['入职日期', '2023-06-01'], ['试用期起始', '2023-06-01'], ['试用期截止', '2023-09-01'], ['合约到期', '长期'],
        ['雇佣类型', '全职'], ['工作地点', emp.subsidiary], ['直属主管', '董事长']
      ]},
      { title: '证件与附件', icon: 'attach', rows: [
        ['NRIC/Passport复印件', '已提交'], ['雇佣合约', '已提交'], ['相关执照', emp.position === '司机' ? '已提交' : '不适用'],
        ['跨境资格/工作许可', '已提交'], ['健康证明', '已提交']
      ]},
      { title: '假期与考勤摘要', icon: 'event', rows: [
        ['年假剩余天数', '10 天'], ['病假剩余天数', '8 天'], ['最后年假日期', '2026-08-10'], ['最后病假日期', '2026-07-05'],
        ['最后事假日期', '2026-06-18'], ['系部联系电话', emp.phone], ['事假记录', '1 次'], ['迟到记录', '0 次'],
        ['旷工记录', '0 次'], ['状态记录', '正常']
      ]},
      { title: '变更记录与认证', icon: 'history', rows: [
        ['修改日期', '2026-09-24'], ['修改操作人', '小聪'], ['变更前', '—'], ['变更后', '—']
      ]}
    ]
  };
}

// ===== 渲染 =====
function pageHeader(title, subtitle, iconName) {
  return '<div class="page-header"><div class="header-row"><span class="header-icon">' + icon(iconName, 'header-icon') + '</span><h1>' + title + '</h1></div><div class="subtitle">' + subtitle + '</div></div>';
}

function renderHome() {
  let cards = SUBSIDIARIES.map(function (s) {
    const link = s.phone ? 'https://wa.me/' + s.phone : '#';
    const desc = s.phone ? 'WhatsApp 联系' : '号码待配置';
    return '<div class="sub-card" onclick="window.open(\'' + link + '\', \'_blank\')">' +
      '<div class="sub-logo">' + icon('chat', 'sub-logo-icon') + '</div>' +
      '<div class="sub-info"><div class="sub-name">' + s.nameZh + '</div><div class="sub-desc">' + desc + '</div></div>' +
      '<div class="sub-arrow">›</div></div>';
  }).join('');
  return pageHeader('首页', '星域控股集团 · 董事长驾驶舱', 'home') +
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
        '<div class="stat-cell"><div class="stat-label">待入职</div><div class="stat-value" style="color:#FFFFFF">' + c.pending + '</div></div>' +
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
    return '<button class="module-btn" data-action="' + action + '">' + icon(m.icon, 'module-icon') + '<span class="label">' + m.label + '</span></button>';
  }).join('');

  return pageHeader('人事', '组织与人才', 'people') +
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
    return '<button class="nav-item" data-key="' + p.key + '">' + icon(p.icon, 'nav-icon') + '<span class="label">' + p.title + '</span></button>';
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

  let html = '<div class="overlay-header"><button class="back" data-back="hr">' + icon('back', 'back-icon') + '</button><h2>员工档案</h2></div>' +
    '<div class="search-box">' + icon('search', 'search-icon') + '<input id="emp-search" type="text" placeholder="搜索ID" /><span class="clear" id="emp-clear" style="display:none">' + icon('close', 'clear-icon') + '</span></div>' +
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
    return '<div class="archive-section"><div class="archive-section-title">' + icon(s.icon, 'section-icon') + s.title + '</div>' + rows + '</div>';
  }).join('');

  return '<div class="overlay-header"><button class="back" data-back="employees">' + icon('back', 'back-icon') + '</button><h2>员工个人档案</h2></div>' +
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
  // 禁止页面缩放（iOS 双指/双击手势）
  document.addEventListener('gesturestart', function (e) { e.preventDefault(); }, { passive: false });
  document.addEventListener('gesturechange', function (e) { e.preventDefault(); }, { passive: false });
  document.addEventListener('gestureend', function (e) { e.preventDefault(); }, { passive: false });
  document.addEventListener('touchmove', function (e) { if (e.touches && e.touches.length > 1) e.preventDefault(); }, { passive: false });
  document.addEventListener('dblclick', function (e) { e.preventDefault(); }, { passive: false });

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
