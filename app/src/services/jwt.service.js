import http from "./http.service";

export function login(username, password, role) {
  return http.post("login", { username, password, role });
}

export function changePassword(username, passwordOld, passwordNew) {
  return http.post("change-pass", { username, passwordOld, passwordNew });
}

export function changeUserPassword(passwordOld, passwordNew) {
  return http.post("common/user/change-pass", { passwordOld, passwordNew });
}

export function getUser(role) {
  return http.get("common/currentuser/" + role);
}

export function logout() {
  return http.get("logout");
}
