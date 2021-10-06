import React, { Component } from "react";
import Card from "../../components/card";
import { getMenu, editMenu, saveMenu } from "./menu.service";
import Toast from "../../components/toast";
import LovInput from "../../components/lov.input";
import MenuList from "./menu.list";
import { lov } from "../../components/lov";
import ReactTooltip from "react-tooltip";

class MenuEdit extends Component {
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
    getMenu(id).then(res => {
      res.data.type = Number.parseInt(res.data.type);
      this.setState(res.data);
    });
  };
  newState = () => {
    return {
      id: "",
      name: "",
      parent: "",
      parentName: "",
      order: "",
      type: 1,
      icon: "",
      path: "",
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
  save = () => {
    if (!this.validator.allValid()) {
      this.validator.showMessages();
      return false;
    }
    let entity = this.state;
    this.state.id === ""
      ? saveMenu(entity).then(res => {
          this.setState({
            id: res.data
          });
          Toast.showMessage({
            customMsg: "رکورد با موفقیت درج شد.",
            type: 4
          });
          this.props.history.push("edit/" + res.data);
        })
      : editMenu(entity);
  };
  clearUser = () => {
    this.setState({
      parent: "",
      parentName: ""
    });
  };
  selectUser = () => {
    lov(MenuList, "منو ها", "menu/list/parent").then(e => {
      this.setState({
        parent: e.id,
        parentName: e.name
      });
    });
  };
  render() {
    let title = this.state.id === "" ? "ثبت" : "ویرایش";
    return (
      <div className="container-fluid">
        <Card title={title + " منو"}>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">
              نام منو
            </label>

            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.name}
              onChange={e => this.setState({ name: e.target.value })}
            />
            {this.validator.message(
              "نام منو",
              this.state.name,
              "required|max:100"
            )}
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2">نام منوی اصلی</label>
            <div className="input-group col-sm-8 col-md-5 col-lg-4">
              <LovInput
                id={this.state.parent}
                name={this.state.parentName}
                clear={this.clearUser}
                select={this.selectUser}
              />
            </div>
          </div>

          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">ترتیب</label>

            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.order}
              onChange={e => this.setState({ order: e.target.value })}
            />
            {this.validator.message(
              "ترتیب",
              this.state.order,
              "required|numeric"
            )}
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2">نوع</label>
            <select
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.type}
              onChange={e => {
                this.setState({ type: Number.parseInt(e.target.value) });
                if (Number.parseInt(e.target.value) === 1) {
                  this.setState({
                    path: "",
                    icon: ""
                  });
                }
              }}
            >
              <option value={1}>منوی اصلی</option>
              <option value={2}>زیر منو</option>
            </select>
          </div>
          <div className="row" style={{ marginBottom: 0 }}>
            <label className="col-sm-4 col-md-3 col-lg-2">کلاس icon</label>

            <input
              type="text"
              className="form-control ltr col-sm-8 col-md-5 col-lg-4"
              value={this.state.icon}
              disabled={this.state.type !== 2}
              onChange={e => this.setState({ icon: e.target.value })}
            />

            <i className={this.state.icon} style={{ padding: 11 }}></i>

            {this.validator.message("کلاس icon", this.state.icon, "max:50")}
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2"></label>
            <a
              style={{ direction: "ltr" }}
              target="_blank"
              href="https://fontawesome.com/v4.7.0/icons/"
              rel="noopener noreferrer"
            >
              https://fontawesome.com/v4.7.0/icons/
            </a>
          </div>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2">مسیر کنترل</label>

            <input
              type="text"
              className="form-control ltr col-sm-8 col-md-5 col-lg-4"
              value={this.state.path}
              onChange={e => this.setState({ path: e.target.value })}
              disabled={this.state.type !== 2}
            />

            {this.state.id !== "" /** && this.state.type === 2 */ ? (
              <React.Fragment>
                <i
                  data-tip
                  data-for="tooltip-path"
                  className="fa fa-warning"
                  style={{ padding: 11 }}
                ></i>

                <ReactTooltip
                  id="tooltip-path"
                  place="left"
                  type="warning"
                  effect="solid"
                >
                  بدون هماهنگی با برنامه‌نویس سامانه مقدار این فیلد را تغییر
                  ندهید.
                </ReactTooltip>
              </React.Fragment>
            ) : null}

            {this.validator.message(
              "مسیر کنترل",
              this.state.path,
              "end_url|max:100"
            )}
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

export default MenuEdit;
