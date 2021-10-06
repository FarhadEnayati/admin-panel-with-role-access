import React, { Component } from "react";
import { withRouter } from "react-router";
import { changePassword } from "../../services/jwt.service";
import Storage from "../storage";
import Toast from "../toast";
import moment from "moment-jalaali";

class ChangePassword extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: props.username,
      defaultPage: props.defaultPage,
      passwordOld: "",
      passwordNew1: "",
      passwordNew2: "",
      loading: false
    };
  }

  handleSubmit = async e => {
    e.preventDefault();
    if (this.state.passwordNew1 !== this.state.passwordNew2) {
      Toast.showMessage({
        customMsg: "کلمه عبور جدید وارد شده و تکرار آن تطابق ندارند."
      });
      return;
    }

    this.setState({ loading: true });
    changePassword(
      this.state.username,
      this.state.passwordOld,
      this.state.passwordNew1
    )
      .then(({ data }) => {
        this.setState({ loading: false });
        Storage.removeItem("lock");
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
        this.props.history.replace(this.state.defaultPage);
      })
      .catch(() => {
        this.setState({
          passwordNew1: "",
          passwordNew2: "",
          loading: false
        });
      });
  };

  render() {
    const input_btn = this.state.loading ? (
      <button className="btn btn-lg btn-bpm btn-block" disabled>
        <span className="spinner-border spinner-border-sm"></span>
        ثبت ..
      </button>
    ) : (
      <button className="btn btn-lg btn-bpm btn-block">ثبت</button>
    );
    return (
      <form
        onSubmit={this.handleSubmit}
        className="rtl form-signin border rounded mx-auto  bg-light shadow mt-5"
      >
        <h1 className="h3 mb-3 font-weight-normal text-center">
          تغییر کلمه عبور
        </h1>
        <input
          type="text"
          id="inputUsername"
          className="form-control"
          placeholder="نام کاربری"
          value={this.state.username}
          disabled
        />
        <input
          type="password"
          id="inputPasswordOld"
          className="form-control"
          placeholder="کلمه عبور قبلی"
          value={this.state.passwordOld}
          onChange={e => this.setState({ passwordOld: e.target.value })}
          required
          autoFocus
        />
        <input
          type="password"
          id="inputPasswordRepeat"
          className="form-control"
          placeholder="کلمه عبور جدید"
          value={this.state.passwordNew1}
          onChange={e => this.setState({ passwordNew1: e.target.value })}
          required
        />
        <input
          type="password"
          id="inputPassword"
          className="form-control"
          placeholder="تکرار کلمه عبور جدید"
          value={this.state.passwordNew2}
          onChange={e => this.setState({ passwordNew2: e.target.value })}
          required
        />
        {input_btn}
      </form>
    );
  }
}
export default withRouter(ChangePassword);
