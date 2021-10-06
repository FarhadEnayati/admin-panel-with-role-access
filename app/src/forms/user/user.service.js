import http from "../../services/http.service";

export function listUser(url) {
  return http.get(url ? url : "user/list");
}

export function getUser(id) {
  return http.get("user/get/" + id);
}

export function saveUser(entity) {
  delete entity.errors;
  return http.post("user/save", entity);
}

export function editUser(entity) {
  delete entity.errors;
  return http.put("user/edit", entity);
}

export function resetPassword(userId) {
  return http.get("user/resetPassword/" + userId);
}
