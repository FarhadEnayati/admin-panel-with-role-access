import http from "../../services/http.service";

export function listRole(url) {
  return http.get(url ? url : "role/list");
}

export function getRole(id) {
  return http.get("role/get/" + id);
}

export function saveRole(entity) {
  delete entity.errors;
  return http.post("role/save", entity);
}

export function editRole(entity) {
  delete entity.errors;
  return http.put("role/edit", entity);
}
