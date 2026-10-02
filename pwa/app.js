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
  close: 'M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z',
  whatsapp: 'M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.612-.916-2.207-.242-.579-.487-.5-.669-.51-.173-.008-.371-.01-.57-.01-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.096 3.2 5.077 4.487.709.306 1.262.489 1.694.625.712.227 1.36.195 1.871.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347m-5.421 7.403h-.004a9.87 9.87 0 01-5.031-1.378l-.361-.214-3.741.982.998-3.648-.235-.374a9.86 9.86 0 01-1.51-5.26c.001-5.45 4.436-9.884 9.888-9.884 2.64 0 5.122 1.03 6.988 2.898a9.825 9.825 0 012.893 6.994c-.003 5.45-4.437 9.884-9.885 9.884m8.413-18.297A11.815 11.815 0 0012.05 0C5.495 0 .16 5.335.157 11.892c0 2.096.547 4.142 1.588 5.945L.057 24l6.305-1.654a11.882 11.882 0 005.683 1.448h.005c6.554 0 11.89-5.335 11.893-11.893a11.821 11.821 0 00-3.48-8.413z',
  add: 'M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z',
  camera: 'M9 2L7.17 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2h-3.17L15 2H9zm3 15c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5z',
  more: 'M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z',
  star: 'M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z',
  group: 'M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z',
  call: 'M6.62 10.79c1.44 2.83 3.76 5.14 6.59 6.59l2.2-2.2c.27-.27.67-.36 1.02-.24 1.12.37 2.33.57 3.57.57.55 0 1 .45 1 1V20c0 .55-.45 1-1 1-9.39 0-17-7.61-17-17 0-.55.45-1 1-1h3.5c.55 0 1 .45 1 1 0 1.25.2 2.45.57 3.57.11.35.03.74-.25 1.02l-2.2 2.2z',
  videocam: 'M17 10.5V7c0-.55-.45-1-1-1H4c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-3.5l4 4v-11l-4 4z',
  mic: 'M12 14c1.66 0 2.99-1.34 2.99-3L15 5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3zm5.3-3c0 3-2.54 5.1-5.3 5.1S6.7 14 6.7 11H5c0 3.41 2.72 6.23 6 6.72V21h2v-3.28c3.28-.48 6-3.3 6-6.72h-1.7z',
  doneall: 'M18 7l-1.41-1.41-6.34 6.34 1.41 1.41L18 7zm4.24-1.41L11.66 16.17 7.48 12l-1.41 1.41L11.66 19l12-12-1.42-1.41zM.41 13.41L6 19l1.41-1.41L1.83 12 .41 13.41z',
  settings: 'M19.14 12.94c.04-.3.06-.61.06-.94 0-.32-.02-.64-.07-.94l2.03-1.58c.18-.14.23-.41.12-.61l-1.92-3.32c-.12-.22-.37-.29-.59-.22l-2.39.96c-.5-.38-1.03-.7-1.62-.94l-.36-2.54c-.04-.24-.24-.41-.48-.41h-3.84c-.24 0-.43.17-.47.41l-.36 2.54c-.59.24-1.13.57-1.62.94l-2.39-.96c-.22-.08-.47 0-.59.22L2.74 8.87c-.12.21-.08.47.12.61l2.03 1.58c-.05.3-.09.63-.09.94s.02.64.07.94l-2.03 1.58c-.18.14-.23.41-.12.61l1.92 3.32c.12.22.37.29.59.22l2.39-.96c.5.38 1.03.7 1.62.94l.36 2.54c.05.24.24.41.48.41h3.84c.24 0 .44-.17.47-.41l.36-2.54c.59-.24 1.13-.56 1.62-.94l2.39.96c.22.08.47 0 .59-.22l1.92-3.32c.12-.22.07-.47-.12-.61l-2.01-1.58zM12 15.6c-1.98 0-3.6-1.62-3.6-3.6s1.62-3.6 3.6-3.6 3.6 1.62 3.6 3.6-1.62 3.6-3.6 3.6z',
  build: 'M22.7 19l-9.1-9.1c.9-2.3.4-5-1.5-6.9-2-2-5-2.4-7.4-1.3L9 6 6 9 1.6 4.7C.4 7.1.9 10.1 2.9 12.1c1.9 1.9 4.6 2.4 6.9 1.5l9.1 9.1c.4.4 1 .4 1.4 0l2.3-2.3c.5-.4.5-1.1.1-1.4z',
  update: 'M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8z',
  emoji: 'M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm3.5-9c.83 0 1.5-.67 1.5-1.5S16.33 8 15.5 8 14 8.67 14 9.5s.67 1.5 1.5 1.5zm-7 0c.83 0 1.5-.67 1.5-1.5S9.33 8 8.5 8 7 8.67 7 9.5 7.67 11 8.5 11zm3.5 6.5c2.33 0 4.31-1.46 5.11-3.5H6.89c.8 2.04 2.78 3.5 5.11 3.5z',
  photo: 'M21 19V5c0-1.1-.9-2-2-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zM8.5 13.5l2.5 3.01L14.5 12l4.5 6H5l3.5-4.5z',
  gif: 'M11.5 9H13v6h-1.5zM9 9H6c-.6 0-1 .5-1 1v4c0 .5.4 1 1 1h3c.6 0 1-.5 1-1v-1H8.5v.5h-2v-3h2V9zM14 9h3c.6 0 1 .5 1 1v4c0 .5-.4 1-1 1h-3c-.6 0-1-.5-1-1v-1h1.5v.5h2v-3h-2V9zM17.5 9H19v6h-1.5z',
  link: 'M3.9 12c0-1.71 1.39-3.1 3.1-3.1h4V7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h4v-1.9H7c-1.71 0-3.1-1.39-3.1-3.1zM8 13h8v-2H8v2zm9-6h-4v1.9h4c1.71 0 3.1 1.39 3.1 3.1s-1.39 3.1-3.1 3.1h-4V17h4c2.76 0 5-2.24 5-5s-2.24-5-5-5z'
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
  profile: { title: '我', subtitle: '董事长', icon: 'person', desc: '个人中心、账号与权限设置等内容将在此呈现。' },
  service: { title: '客服板块', subtitle: '客户服务', icon: 'chat', desc: '客户咨询、售后服务、工单处理等内容将在此呈现。' },
  ops: { title: '运营板块', subtitle: '业务运营', icon: 'work', desc: '业务运营、数据分析、活动策划等内容将在此呈现。' },
  tbd: { title: '待定板块', subtitle: '功能规划中', icon: 'badge', desc: '功能规划中，内容待定。' }
};

const HOME_GROUPS = [
  { title: '职能管理', items: [
    { label: '人事', icon: 'people', key: 'hr' },
    { label: '财务', icon: 'wallet', key: 'finance' },
    { label: '法务', icon: 'gavel', key: 'legal' },
    { label: '行政', icon: 'business', key: 'admin' }
  ]},
  { title: '运营服务', items: [
    { label: '内审', icon: 'shield', key: 'audit' },
    { label: '客服', icon: 'chat', key: 'service' },
    { label: '运营', icon: 'work', key: 'ops' },
    { label: '待定', icon: 'badge', key: 'tbd' }
  ]}
];

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
function pageHeader(title, subtitle, iconName, backKey) {
  const back = backKey ? '<button class="back-btn" data-nav="' + backKey + '">' + icon('back', 'back-icon') + '</button>' : '';
  return '<div class="page-header"><div class="header-row">' + back + '<h1>' + title + '</h1></div><div class="subtitle">' + subtitle + '</div></div>';
}

function renderHome() {
  const g = COMPANY_STATS[0];
  const now = new Date();
  const dateStr = now.getFullYear() + ' 年 ' + (now.getMonth() + 1) + ' 月 ' + now.getDate() + ' 日';
  const summary = '<div class="card summary-card">' +
    '<div class="stats-head"><span class="stats-zh">今日摘要</span><span class="stats-note">' + dateStr + '</span></div>' +
    '<div class="stats-total-label">集团总员工</div>' +
    '<div class="stats-total">' + g.total + '</div>' +
    '<div class="stats-divider"></div>' +
    '<div class="stats-row">' +
      '<div class="stat-cell"><div class="stat-label">在职</div><div class="stat-value">' + g.active + '</div></div>' +
      '<div class="stat-cell"><div class="stat-label">待入职</div><div class="stat-value">' + g.pending + '</div></div>' +
      '<div class="stat-cell"><div class="stat-label">子公司</div><div class="stat-value">' + SUBSIDIARIES.length + '</div></div>' +
    '</div></div>';
  const groups = HOME_GROUPS.map(function (grp) {
    const btns = grp.items.map(function (b) {
      return '<button class="module-btn" data-nav="' + b.key + '">' + icon(b.icon, 'module-icon') + '<span class="label">' + b.label + '</span></button>';
    }).join('');
    return '<div class="section-title">' + grp.title + '</div><div class="module-grid">' + btns + '</div>';
  }).join('');
  return pageHeader('首页', '星域控股集团 · 董事长驾驶舱', 'home') +
    '<div class="page-body">' +
    summary +
    '<div style="height:16px"></div>' +
    groups +
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

let currentWhatsApp = null;

function renderService() {
  const cards = SUBSIDIARIES.map(function (s) {
    return '<div class="sub-card">' +
      '<div class="sub-logo">' + icon('whatsapp', 'sub-logo-icon') + '</div>' +
      '<div class="sub-info"><div class="sub-name">' + s.nameZh + '</div><div class="sub-desc">' + s.nameEn + '</div></div>' +
      '<button class="wa-btn" data-wa="' + s.nameZh + '">' + icon('whatsapp', 'wa-icon') + '<span>WhatsApp</span></button>' +
      '</div>';
  }).join('');
  return pageHeader('客服', '客户服务', 'chat', 'home') +
    '<div class="page-body">' + cards + '</div>';
}

const WA_TABS = [
  { key: 'updates', label: '更新', icon: 'update' },
  { key: 'calls', label: '通话', icon: 'call' },
  { key: 'tools', label: '工具', icon: 'build' },
  { key: 'chats', label: '聊天', icon: 'chat' },
  { key: 'settings', label: '设置', icon: 'settings' }
];

// ===== Supabase 实时数据（集团枢纽 SEH） =====
let sehClient = null;
let waContacts = [];
let waChatMsgs = [];
let waChatMsisdn = null;
let waLoaded = false;

function sehInit() {
  if (sehClient) return sehClient;
  if (!window.supabase || !window.SEH_SUPABASE_URL || !window.SEH_SUPABASE_ANON_KEY) return null;
  sehClient = window.supabase.createClient(window.SEH_SUPABASE_URL, window.SEH_SUPABASE_ANON_KEY);
  return sehClient;
}

function waFmtTime(iso) {
  if (!iso) return '';
  var d = new Date(iso);
  var hh = ('0' + d.getHours()).slice(-2);
  var mm = ('0' + d.getMinutes()).slice(-2);
  return hh + ':' + mm;
}

function waScreenOpen() {
  var s = document.getElementById('wa-screen');
  return s && !s.classList.contains('hidden');
}

async function waLoadContacts() {
  var c = sehInit();
  if (!c) { waLoaded = true; return; }
  try {
    var contactsRes = await c.from('whatsapp_contacts').select('msisdn, display_name').order('updated_at', { ascending: false });
    var eventsRes = await c.from('whatsapp_events').select('from_msisdn, text_body, created_at').order('created_at', { ascending: false }).limit(500);
    var repliesRes = await c.from('whatsapp_replies').select('to_msisdn, body_text, created_at').order('created_at', { ascending: false }).limit(500);

    var nameMap = {};
    (contactsRes.data || []).forEach(function (r) { nameMap[r.msisdn] = r.display_name; });

    var last = {};
    (eventsRes.data || []).forEach(function (e) {
      if (!last[e.from_msisdn] || e.created_at > last[e.from_msisdn].t) last[e.from_msisdn] = { text: e.text_body, t: e.created_at };
    });
    (repliesRes.data || []).forEach(function (r) {
      if (!last[r.to_msisdn] || r.created_at > last[r.to_msisdn].t) last[r.to_msisdn] = { text: r.body_text, t: r.created_at };
    });

    var list = [];
    var seen = {};
    Object.keys(nameMap).forEach(function (m) {
      seen[m] = true;
      var lm = last[m];
      list.push({ msisdn: m, name: nameMap[m] || m, lastMessage: lm ? lm.text : '', time: lm ? waFmtTime(lm.t) : '', t: lm ? lm.t : '', unread: 0, favorite: false, isGroup: false });
    });
    Object.keys(last).forEach(function (m) {
      if (seen[m]) return;
      list.push({ msisdn: m, name: m, lastMessage: last[m].text, time: waFmtTime(last[m].t), t: last[m].t, unread: 0, favorite: false, isGroup: false });
    });
    list.sort(function (a, b) { return (a.t || '') < (b.t || '') ? 1 : -1; });
    waContacts = list;
    waRecents = list.slice(0, 4).map(function (c) { return { name: c.name, phone: c.msisdn }; });
  } catch (e) { /* 忽略 */ }
  waLoaded = true;
}

async function waLoadChat(msisdn) {
  var c = sehInit();
  if (!c) return;
  try {
    var eRes = await c.from('whatsapp_events').select('text_body, created_at').eq('from_msisdn', msisdn).order('created_at', { ascending: true });
    var rRes = await c.from('whatsapp_replies').select('body_text, created_at').eq('to_msisdn', msisdn).order('created_at', { ascending: true });
    var raw = [];
    (eRes.data || []).forEach(function (e) { raw.push({ text: e.text_body, isSent: false, t: e.created_at }); });
    (rRes.data || []).forEach(function (r) { raw.push({ text: r.body_text, isSent: true, t: r.created_at }); });
    raw.sort(function (a, b) { return a.t < b.t ? -1 : 1; });
    waChatMsgs = raw.map(function (m) { return { text: m.text, isSent: m.isSent, time: waFmtTime(m.t) }; });
    waChatMsisdn = msisdn;
  } catch (e) { /* 忽略 */ }
}

function waSubscribe() {
  var c = sehInit();
  if (!c) return;
  try {
    c.channel('seh-wa-realtime')
      .on('postgres_changes', { event: '*', schema: 'public', table: 'whatsapp_contacts' }, function () { waRefresh(); })
      .on('postgres_changes', { event: '*', schema: 'public', table: 'whatsapp_events' }, function () { waRefresh(); })
      .on('postgres_changes', { event: '*', schema: 'public', table: 'whatsapp_replies' }, function () { waRefresh(); })
      .subscribe();
  } catch (e) { /* 忽略 */ }
}

function waRefresh() {
  waLoadContacts().then(function () {
    if (waScreenOpen()) renderWa();
    if (waChatMsisdn) {
      waLoadChat(waChatMsisdn).then(function () { if (waScreenOpen()) renderWa(); });
    }
  });
}

let waTab = 'chats';
let waChatContact = null;
let waFilter = '全部';
let waSearchOpen = false;
let waRecents = [];

function openWhatsApp(store) {
  currentWhatsApp = store || currentWhatsApp || '炙巷食铺';
  waTab = 'chats';
  waChatContact = null;
  waSearchOpen = false;
  const screen = document.getElementById('wa-screen');
  screen.classList.remove('hidden');
  renderWa();
  sehInit();
  waSubscribe();
  waRefresh();
}

function closeWhatsApp() {
  const screen = document.getElementById('wa-screen');
  screen.classList.add('hidden');
  screen.innerHTML = '';
}

function renderWa() {
  const screen = document.getElementById('wa-screen');
  if (waChatContact) screen.innerHTML = waChatHtml(waChatContact);
  else if (waSearchOpen) screen.innerHTML = waSearchHtml();
  else if (waTab !== 'chats') screen.innerHTML = waPlaceholderHtml();
  else screen.innerHTML = waListHtml();
  moveWaIndicator();
  bindWaScroll();
}

function moveWaIndicator() {
  const active = document.querySelector('.wa-tab.active');
  const ind = document.querySelector('.wa-tab-indicator');
  if (!active || !ind) return;
  ind.style.transform = 'translateX(' + active.offsetLeft + 'px)';
  ind.style.width = active.offsetWidth + 'px';
}

function bindWaScroll() {
  const scroll = document.querySelector('.wa-scroll');
  const header = document.querySelector('.wa-header-list');
  if (!scroll || !header) return;
  const update = function () {
    header.classList.toggle('scrolled', scroll.scrollTop > 8);
  };
  update();
  scroll.addEventListener('scroll', update, { passive: true });
}

function waHeaderHtml(backAction, extraClass) {
  return '<div class="wa-header' + (extraClass ? ' ' + extraClass : '') + '">' +
    '<button class="wa-ico-btn" data-wa-action="' + backAction + '">' + icon('back', 'wa-ico') + '</button>' +
    '<div class="wa-header-title">聊天</div>' +
    '<button class="wa-ico-btn">' + icon('camera', 'wa-ico') + '</button>' +
    '<button class="wa-add-btn">' + icon('add', 'wa-add-icon') + '</button>' +
    '</div>';
}

function waTabsHtml() {
  return '<div class="wa-tabs"><div class="wa-tabs-inner"><div class="wa-tab-indicator"></div>' + WA_TABS.map(function (t) {
    return '<button class="wa-tab' + (t.key === waTab ? ' active' : '') + '" data-wa-tab="' + t.key + '">' + icon(t.icon, 'wa-tab-icon') + '<span class="wa-tab-label">' + t.label + '</span></button>';
  }).join('') + '</div></div>';
}

function waListHtml() {
  if (!waLoaded) {
    return '<div class="wa-wallpaper">' + waHeaderHtml('back', 'wa-header-list') +
      '<div class="wa-scroll"><div class="wa-title">聊天</div>' +
      '<div class="wa-ph-desc" style="text-align:center;padding:40px 0">加载中…</div></div>' +
      waTabsHtml() + '</div>';
  }
  const list = waContacts.filter(function (c) {
    if (waFilter === '未读') return c.unread > 0;
    if (waFilter === '特别关注') return c.favorite;
    if (waFilter === '群组') return c.isGroup;
    return true;
  });

  const filters = ['全部', '未读', '特别关注', '群组'].map(function (f) {
    return '<button class="wa-filter' + (f === waFilter ? ' active' : '') + '" data-wa-filter="' + f + '">' + f + '</button>';
  }).join('');

  const contacts = list.map(function (c) {
    const avatar = c.isGroup ? icon('group', 'wa-avatar-icon') : '<span>' + c.name.charAt(0) + '</span>';
    const star = c.favorite ? icon('star', 'wa-star') : '';
    const unread = c.unread > 0 ? '<span class="wa-unread">' + c.unread + '</span>' : '';
    return '<div class="wa-contact" data-wa-contact="' + c.msisdn + '">' +
      '<div class="wa-avatar">' + avatar + '</div>' +
      '<div class="wa-contact-body">' +
        '<div class="wa-contact-top"><span class="wa-contact-name">' + c.name + '</span>' + star + '</div>' +
        '<div class="wa-contact-msg">' + c.lastMessage + '</div>' +
      '</div>' +
      '<div class="wa-contact-side"><span class="wa-time">' + c.time + '</span>' + unread + '</div>' +
      '</div>';
  }).join('');

  return '<div class="wa-wallpaper">' +
    waHeaderHtml('back', 'wa-header-list') +
    '<div class="wa-scroll">' +
      '<div class="wa-title">聊天</div>' +
      '<div class="wa-search" data-wa-action="open-search">' + icon('search', 'wa-search-icon') + '<span>搜索</span></div>' +
      '<div class="wa-filters">' + filters + '</div>' +
      contacts +
    '</div>' +
    waTabsHtml() +
    '</div>';
}

function waSearchHtml() {
  const recentItems = waRecents.map(function (c) {
    return '<div class="wa-recent-item" data-wa-contact="' + c.phone + '">' +
      '<div class="wa-avatar wa-avatar-sm"><span>' + c.name.charAt(0) + '</span></div>' +
      '<div class="wa-recent-body"><div class="wa-recent-name">' + c.name + '</div><div class="wa-recent-phone">' + c.phone + '</div></div>' +
      '</div>';
  }).join('');

  const recentSection = waRecents.length > 0 ?
    '<div class="wa-recent-head"><span class="wa-recent-title">最近搜索</span><button class="wa-clear-btn" data-wa-action="clear-recent">全部清除</button></div>' +
    '<div class="wa-recent-list">' + recentItems + '</div>' : '';

  const mediaFilters = [
    { label: '照片', icon: 'photo' },
    { label: '动图', icon: 'gif' },
    { label: '链接', icon: 'link' },
    { label: '视频', icon: 'videocam' },
    { label: '文档', icon: 'description' },
    { label: '音频', icon: 'mic' },
    { label: '投票', icon: 'chart' },
    { label: '活动', icon: 'event' }
  ];
  const mediaItems = mediaFilters.map(function (m) {
    return '<button class="wa-media-item">' + icon(m.icon, 'wa-media-icon') + '<span>' + m.label + '</span></button>';
  }).join('');

  return '<div class="wa-wallpaper">' +
    '<div class="wa-header wa-header-search">' +
      '<button class="wa-ico-btn" data-wa-action="search-back">' + icon('back', 'wa-ico') + '</button>' +
      '<div class="wa-search-box">' + icon('search', 'wa-search-icon') + '<input type="text" placeholder="搜索" class="wa-search-input"></div>' +
    '</div>' +
    '<div class="wa-scroll wa-search-scroll">' +
      recentSection +
      '<div class="wa-media-title">影音内容</div>' +
      '<div class="wa-media-grid">' + mediaItems + '</div>' +
    '</div>' +
    '</div>';
}

function waPlaceholderHtml() {
  const tab = WA_TABS.find(function (t) { return t.key === waTab; });
  return '<div class="wa-wallpaper">' +
    waHeaderHtml('back') +
    '<div class="wa-placeholder"><div class="wa-ph-title">' + (tab ? tab.label : '') + '</div><div class="wa-ph-desc">功能待接入</div></div>' +
    waTabsHtml() +
    '</div>';
}

function waChatHtml(msisdn) {
  var contact = null;
  for (var i = 0; i < waContacts.length; i++) { if (waContacts[i].msisdn === msisdn) { contact = waContacts[i]; break; } }
  var name = contact ? contact.name : msisdn;
  const header = '<div class="wa-header">' +
    '<button class="wa-ico-btn" data-wa-action="chat-back">' + icon('back', 'wa-ico') + '</button>' +
    '<div class="wa-avatar wa-avatar-sm"><span>' + name.charAt(0) + '</span></div>' +
    '<div class="wa-header-title wa-header-title-left"><div class="wa-chat-name">' + name + '</div><div class="wa-chat-status">在线</div></div>' +
    '<button class="wa-ico-btn">' + icon('videocam', 'wa-ico') + '</button>' +
    '<button class="wa-ico-btn">' + icon('call', 'wa-ico') + '</button>' +
    '</div>';

  var msgs;
  if (!waChatMsgs.length) {
    msgs = '<div class="wa-ph-desc" style="text-align:center;padding:40px 0">暂无消息</div>';
  } else {
    msgs = waChatMsgs.map(function (m) {
      const cls = m.isSent ? ' sent' : ' recv';
      const tick = m.isSent ? icon('doneall', 'wa-tick') : '';
      return '<div class="wa-msg' + cls + '"><div class="wa-bubble' + cls + '"><span class="wa-bubble-text">' + m.text + '</span><span class="wa-msg-time">' + m.time + tick + '</span></div></div>';
    }).join('');
  }

  const inputBar = '<div class="wa-input-bar">' +
    '<button class="wa-ico-btn">' + icon('add', 'wa-ico') + '</button>' +
    '<div class="wa-input">消息</div>' +
    '<button class="wa-ico-btn">' + icon('emoji', 'wa-ico') + '</button>' +
    '<button class="wa-ico-btn">' + icon('camera', 'wa-ico') + '</button>' +
    '<button class="wa-mic-btn">' + icon('mic', 'wa-ico') + '</button>' +
    '</div>';

  return '<div class="wa-wallpaper">' + header + '<div class="wa-chat-scroll">' + msgs + '</div>' + inputBar + '</div>';
}

function renderPlaceholder(key) {
  const p = PLACEHOLDERS[key];
  return pageHeader(p.title, p.subtitle, p.icon) +
    '<div class="page-body"><div class="placeholder-title">' + p.title + '</div><div class="placeholder-desc">' + p.desc + '</div></div>';
}

function renderPage(key) {
  if (key === 'home') return renderHome();
  if (key === 'hr') return renderHr();
  if (key === 'service') return renderService();
  return renderPlaceholder(key);
}

function renderNav() {
  const items = PAGES.map(function (p) {
    return '<button class="nav-item" data-key="' + p.key + '">' + icon(p.icon, 'nav-icon') + '<span class="label">' + p.title + '</span></button>';
  }).join('');
  return '<div class="bottom-nav-inner"><div class="nav-indicator"></div>' + items + '</div>';
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
  moveIndicator();
  contentEl().scrollTop = 0;
}

function moveIndicator() {
  const active = document.querySelector('.nav-item.active');
  const ind = document.querySelector('.nav-indicator');
  if (!active || !ind) return;
  ind.style.transform = 'translateX(' + active.offsetLeft + 'px)';
  ind.style.width = active.offsetWidth + 'px';
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
  window.addEventListener('resize', moveIndicator);
  document.addEventListener('click', function (e) {
    const mod = e.target.closest('.module-btn');
    if (!mod) {
      const back = e.target.closest('.back-btn');
      if (back) { navigate(back.getAttribute('data-nav')); }
      return;
    }
    const nav = mod.getAttribute('data-nav');
    if (nav) { navigate(nav); return; }
    if (mod.getAttribute('data-action') === 'employees') openEmployees();
  });
  document.addEventListener('click', function (e) {
    const wa = e.target.closest('.wa-btn');
    if (!wa) return;
    openWhatsApp(wa.getAttribute('data-wa'));
  });
  document.addEventListener('click', function (e) {
    const tab = e.target.closest('[data-wa-tab]');
    if (tab) { waTab = tab.getAttribute('data-wa-tab'); renderWa(); return; }
    const contact = e.target.closest('[data-wa-contact]');
    if (contact) {
      const m = contact.getAttribute('data-wa-contact');
      waChatContact = m;
      renderWa();
      waLoadChat(m).then(function () { if (waScreenOpen() && waChatContact === m) renderWa(); });
      return;
    }
    const filter = e.target.closest('[data-wa-filter]');
    if (filter) { waFilter = filter.getAttribute('data-wa-filter'); renderWa(); return; }
    const action = e.target.closest('[data-wa-action]');
    if (action) {
      const a = action.getAttribute('data-wa-action');
      if (a === 'back') closeWhatsApp();
      else if (a === 'chat-back') { waChatContact = null; renderWa(); }
      else if (a === 'open-search') { waSearchOpen = true; renderWa(); }
      else if (a === 'search-back') { waSearchOpen = false; renderWa(); }
      else if (a === 'clear-recent') { waRecents = []; renderWa(); }
    }
  });
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/sw.js').catch(function () {});
  }
}

init();
