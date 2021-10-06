import React, { Component } from "react";
import Card from "../../components/card";
import { lov } from "../../components/lov";
import MenuList from "../menu/menu.list";
import LovInput from "../../components/lov.input";
import RoleList from "../role/role.list";
import { getMenuRole, saveMenuRole, editMenuRole } from "./menu.role.service";
import Toast from "../../components/toast";

class MenuRoleEdit extends Component {
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
    getMenuRole(id).then(res => {
      this.setState(res.data);
    });
  };
  newState = () => {
    return {
      id: "",
      menuId: "",
      menuName: "",
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
  clearMenu = () => {
    this.setState({
      menuId: "",
      menuName: ""
    });
  };
  selectMenu = () => {
    lov(MenuList, "منوهای سیستم").then(e => {
      this.setState({
        menuId: e.id,
        menuName: e.name
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
      ? saveMenuRole(entity).then(res => {
          this.setState({
            id: res.data
          });
          Toast.showMessage({
            customMsg: "رکورد با موفقیت درج شد.",
            type: 4
          });
          this.props.history.push("edit/" + res.data);
        })
      : editMenuRole(entity);
  };
  render() {
    let title = this.state.id === "" ? "ثبت" : "ویرایش";
    return (
      <div className="container-fluid">
        <Card title={title + " تخصیص منو به نقش"}>
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
            <label className="col-sm-4 col-md-3 col-lg-2 required">منو</label>
            <div className="input-group col-sm-8 col-md-9 col-lg-4">
              <LovInput
                id={this.state.menuId}
                name={this.state.menuName}
                clear={this.clearMenu}
                select={this.selectMenu}
              />
            </div>
            {this.validator.message("منو", this.state.menuId, "required")}
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

export default MenuRoleEdit;
