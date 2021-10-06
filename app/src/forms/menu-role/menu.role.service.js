import http from "../../services/http.service";

export function listMenuRole(url) {
  return http.get(url ? url : "menu-role/list");
}

export function getMenuRole(id) {
  return http.get("menu-role/get/" + id);
}

export function saveMenuRole(entity) {
  delete entity.errors;
  return http.post("menu-role/save", entity);
}

export function editMenuRole(entity) {
  delete entity.errors;
  return http.put("menu-role/edit", entity);
}
