import axios from "axios";
import Storage from "../components/storage";
import Toast from "../components/toast";
import packageJson from "../../package.json";
// import $ from "jquery";

let showMessage = true;

axios.interceptors.request.use(
  config => {
    if (["login", "logout", "change-pass"].includes(config.url)) {
      config.baseURL = "/" + packageJson.homepage + "/jwt/";
      return config;
    }
    config.baseURL = "/" + packageJson.homepage + "/api/";
    let token = Storage.getItem("token");
    if (token) {
      // axios.defaults.headers.common['X-AUTH-TOKEN'] = token;
      config.headers["X-AUTH-TOKEN"] = `Bearer ${token}`;
      return config;
    }        
    return Promise.reject();
  },
  error => {    
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  success => {
    // $("#cover-spin").css("display", "none");
    let spin = document.querySelector("#cover-spin");
    if (spin != null) {
      spin.style.display = "none";
    }  
    if (success && success.status === 202) {
      if (success.data.exception) {
        Toast.showMessage(success.data.exception);
      } else {
        Toast.showMessage(success.data);
      }
    }
    return success;
  },
  error => {    
    if (error && error.response && showMessage) {
      if ([500, 401].includes(error.response.status)) {        
        if (error.response.data && error.response.data.customMsg) {          
          Toast.showMessage(error.response.data);
        } else {
          showMessage = false;
          Toast.showMessage({
            customMsg: "خطا در ارتباط با سرور رخ داده است.",
            options: {
              onClose: () => {
                showMessage = true;
              }
            }
          });
        }
      } else if (error.response.status === 403) {
        window.location = "/" + packageJson.homepage + "/login";
        return;
      }
      // $("#cover-spin").css("display", "none");
      let spin = document.querySelector("#cover-spin");
      if (spin !== null) {
        spin.style.display = "none";
      }
    }
    return Promise.reject(
      error && error.response ? error.response.data : false
    );
  }
);

export default {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete
};
