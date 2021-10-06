import React, { Component } from "react";
import Card from "../../components/card";
import { lov } from "../../components/lov";
import UserList from "../user/user.list";
import LovInput from "../../components/lov.input";
import RoleList from "../role/role.list";
import { getRoleUser, saveRoleUser, editRoleUser } from "./role.user.service";
import Toast from "../../components/toast";

class RoleUserEdit extends Component {
  constructor(props) {
    super(props);
    this.state = this.newState();
    this.validator = this.props.validator;
  }
  componentDidMount() {
    let params = this.props.match.params;
    if (params.id) {
      this.getData(params.id);
    }
  }
  getData = id => {
    getRoleUser(id).then(res => {
      this.setState(res.data);
    });
  };
  newState = () => {
    return {
      id: "",
      userId: "",
      userName: "",
      roleId: "",
      roleName: "",
      status: 1
    };
  };
  newEntity = () => {
    this.setState(this.newState());
    if (this.props.match.params.id != null) {
      this.props.history.push("../add", {
        id: this.props.match.params.id
      });
    }
    this.validator.hideMessages();
  };
  clearUser = () => {
    this.setState({
      userId: "",
      userName: ""
    });
  };
  selectUser = () => {
    lov(UserList, "کاربران سیستم").then(e => {
      this.setState({
        userId: e.id,
        userName: e.userName
      });
    });
  };
  clearRole = () => {
    this.setState({
      roleId: "",
      roleName: ""
    });
  };
  selectRole = () => {
    lov(RoleList, "نقش سیستم").then(e => {
      this.setState({
        roleId: e.id,
        roleName: e.name
      });
    });
  };
  save = () => {
    if (!this.validator.allValid()) {
      this.validator.showMessages();
      return false;
    }
    let entity = this.state;
    this.state.id === ""
      ? saveRoleUser(entity).then(res => {
          this.setState({
            id: res.data
          });
          Toast.showMessage({
            customMsg: "رکورد با موفقیت درج شد.",
            type: 4
          });
          this.props.history.push("edit/" + res.data);
        })
      : editRoleUser(entity);
  };
  render() {
    let title = this.state.id === "" ? "ثبت" : "ویرایش";
    return (
      <div className="container-fluid">
        <Card title={title + " نقش کاربر"}>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">کاربر</label>
            <div className="input-group col-sm-8 col-md-9 col-lg-4">
              <LovInput
                id={this.state.userId}
                name={this.state.userName}
                clear={this.clearUser}
                select={this.selectUser}
              />
            </div>
            {this.validator.message("کاربر", this.state.userId, "required")}
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">نقش</label>
            <div className="input-group col-sm-8 col-md-9 col-lg-4">
              <LovInput
                id={this.state.roleId}
                name={this.state.roleName}
                clear={this.clearRole}
                select={this.selectRole}
              />
            </div>
            {this.validator.message("نقش", this.state.roleId, "required")}
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">وضعیت</label>
            <label htmlFor="status_1">
              <input
                type="radio"
                name="status"
                id="status_1"
                value={1}
                checked={Number.parseInt(this.state.status) === 1}
                onChange={e => this.setState({ status: e.target.value })}
              />
              فعال
            </label>
            &nbsp;
            <label htmlFor="status_2">
              <input
                type="radio"
                name="status"
                id="status_2"
                value={0}
                checked={Number.parseInt(this.state.status) === 0}
                onChange={e => this.setState({ status: e.target.value })}
              />
              غیرفعال
            </label>
          </div>

          <div className="btn-group">
            <button
              className="btn btn-outline-dark"
              onClick={() => {
                this.props.history.goBack();
                if (this.props.location.state !== undefined) {
                  this.getData(this.props.location.state.id);
                }
              }}
            >
              بازگشت
            </button>
            <button className="btn btn-secondary" onClick={this.newEntity}>
              جدید
            </button>
            <button className="btn btn-bpm" onClick={this.save}>
              {title}
            </button>
          </div>
        </Card>
      </div>
    );
  }
}

export default RoleUserEdit;
