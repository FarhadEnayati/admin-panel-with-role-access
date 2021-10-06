import React, { Component } from "react";
import { changeUserPassword, getUser } from "../services/jwt.service";
import { withRouter } from "react-router";
import Toast from "../components/toast";

class ChangePassword extends Component {
  constructor(props) {
    super(props);
    let username = "";
    if (this.props.location.state != null) {
      username = this.props.location.state.username;
    }
    this.state = {
      username: username,
      defaultPage: props.defaultPage,
      passwordOld: "",
      passwordNew1: "",
      passwordNew2: "",
      loading: false
    };
  }
  async componentDidMount() {
    if (this.state.username === "") {
      const { data: user } = await getUser();
      this.setState({ username: user.userName });
    }
  }
  handleSubmit = async e => {
    e.preventDefault();
    if (this.state.passwordNew1 !== this.state.passwordNew2) {
      Toast.showMessage({
        customMsg: "کلمه عبور جدید وارد شده و تکرار آن تطابق ندارند."
      });
      return;
    }
    try {
      this.setState({ loading: true });
      await changeUserPassword(this.state.passwordOld, this.state.passwordNew1);
    } finally {
      this.setState({
        passwordNew1: "",
        passwordNew2: "",
        loading: false
      });
    }
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
      <div className="row">
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
      </div>
    );
  }
}

export default withRouter(ChangePassword);
