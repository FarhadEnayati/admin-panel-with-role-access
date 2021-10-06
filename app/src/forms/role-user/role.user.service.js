import http from "../../services/http.service";

export function listRoleUser(url) {
  return http.get(url ? url : "role-user/list");
}

export function getRoleUser(id) {
  return http.get("role-user/get/" + id);
}

export function saveRoleUser(entity) {
  delete entity.errors;
  return http.post("role-user/save", entity);
}

export function editRoleUser(entity) {
  delete entity.errors;
  return http.put("role-user/edit", entity);
}
