import React, { Component } from "react";
import { login } from "../../services/jwt.service";
import Storage from "../storage";
import ChangePassword from "./change-pass";
import RoleSelector from "./role-selector";
import { Redirect } from "react-router-dom";
import { Spinner } from "reactstrap";
import Toast from "../toast";
import "./../../css/login.css";
import moment from "moment-jalaali";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      loading: false,
      defaultPage: "/",
      lock: false,
      roles: [],
      token: null
    };
  }

  componentDidMount() {
    Storage.removeItem("token", "lock", "role", "menu", "expiration");
  }

  handleSubmit = async e => {
    e.preventDefault();
    this.setState({ loading: true });
    login(this.state.username, this.state.password, null)
      .then(({ data }) => {
        if (data.expiration != null) {
          Storage.setItem("expiration", moment().add(data.expiration));
        }
        if (data.selectedRole != null) {
          Storage.setItem("role", data.selectedRole);
        }
        if (data.token != null) {
          Toast.dismiss();
          Storage.setItem("token", data.token);
        }

        this.setState({
          loading: false,
          token: data.token,
          roles: data.roles
        });
      })
      .catch(() => {
        this.setState({
          password: "",
          loading: false,
          lock: Storage.getItem("lock")
        });
      });
  };

  render() {
    let { token, roles } = this.state;
    if (token) {
      return <Redirect to={this.state.defaultPage} />;
    } else if (roles && roles.length > 1) {
      return (
        <RoleSelector
          username={this.state.username}
          password={this.state.password}
          defaultPage={this.state.defaultPage}
          roles={this.state.roles}
        />
      );
    }
    const input_btn = this.state.loading ? (
      <button className="btn btn-lg btn-bpm btn-block" disabled>
        <Spinner type="grow" color="light" />
      </button>
    ) : (
      <button className="btn btn-lg btn-bpm btn-block">ورود</button>
    );
    const { lock } = this.state;
    return lock ? (
      <ChangePassword
        username={this.state.username}
        defaultPage={this.state.defaultPage}
      />
    ) : (
      <form
        onSubmit={this.handleSubmit}
        className="rtl form-signin border rounded mx-auto  bg-light shadow mt-5"
      >
        <h1 className="h3 mb-3 font-weight-normal text-center">
          ورود به سامانه
        </h1>
        <input
          type="text"
          id="inputUsername"
          className="form-control"
          placeholder="نام کاربری"
          value={this.state.username}
          onChange={e => this.setState({ username: e.target.value })}
          required
          autoFocus
        />
        <input
          type="password"
          id="inputPassword"
          className="form-control"
          placeholder="کلمه عبور"
          value={this.state.password}
          autoComplete="off"
          onChange={e => this.setState({ password: e.target.value })}
          required
        />
        {input_btn}
      </form>
    );
  }
}

export default Login;
