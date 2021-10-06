import http from "../../services/http.service";

export function listMenu(url) {
  return http.get(url ? url : "menu/list");
}

export function getMenu(id) {
  return http.get("menu/get/" + id);
}

export function saveMenu(entity) {
  delete entity.errors;
  return http.post("menu/save", entity);
}

export function editMenu(entity) {
  delete entity.errors;
  return http.put("menu/edit", entity);
}
