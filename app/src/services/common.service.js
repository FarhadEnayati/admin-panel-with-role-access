import http from "./http.service";

export function getDate() {
  return http.get("common/date");
}

export function listUserMenus(role) {
  return http.get("common/menu/role/" + role);
}
