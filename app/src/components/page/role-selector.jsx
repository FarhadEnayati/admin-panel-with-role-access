import React, { Component } from "react";
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import { login } from "../../services/jwt.service";
import Storage from "../storage";
import { Spinner } from "reactstrap";
import Toast from "../toast";
import moment from "moment-jalaali";

class RoleSelector extends Component {
  constructor(props) {
    super(props);
    this.state = {
      roles: props.roles,
      defaultPage: props.defaultPage,
      selected: "",
      token: null,
      role: null,
      loading: false
    };
    Toast.dismiss();
  }

  handleSubmit = async e => {
    e.preventDefault();
    this.setState({ loading: true });
    let role = this.state.selected;
    login(this.props.username, this.props.password, role)
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
          token: data.token,
          role: data.selectedRole,
          loading: false
        });
      })
      .catch(() => {
        this.setState({ loading: false });
      });
  };

  render() {
    const { roles, role, token } = this.state;
    if (token && role) {
      return <Redirect to={this.state.defaultPage} />;
    }
    const input_btn = this.state.loading ? (
      <button className="btn btn-lg btn-bpm btn-block" disabled>
        <Spinner type="grow" color="light" />
      </button>
    ) : (
      <button
        disabled={!this.state.selected}
        className="btn btn-lg btn-bpm btn-block"
      >
        انتخاب
      </button>
    );
    return (
      <form
        onSubmit={this.handleSubmit}
        className="rtl form-signin border rounded mx-auto  bg-light shadow mt-5"
      >
        <h1 className="h3 mb-3 font-weight-normal text-center">انتخاب نقش</h1>

        <select
          className="form-control"
          onChange={e => {
            this.setState({
              selected: e.target.value
            });
          }}
          style={{ marginBottom: 10 }}
          size={1}
        >
          <option value="">یک گزینه را انتخاب نمایید.</option>
          {roles.map(role => (
            <option key={role.id} value={role.id}>
              {role.name}
            </option>
          ))}
        </select>
        {input_btn}
      </form>
    );
  }
}
export default withRouter(RoleSelector);
