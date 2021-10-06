import { Component } from "react";
import Storage from "./storage";
import Toast from "./toast";
import { logout } from "../services/jwt.service";

class Logout extends Component {
  async componentDidMount() {
    await Storage.removeItem("expiration");
    await Storage.removeItem("token");
    Toast.dismiss();
    logout().finally(() => {
      this.props.history.replace("login");
    });
  }

  render() {
    return null;
  }
}

export default Logout;
