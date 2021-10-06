import React, { Component } from "react";
import Card from "../../components/card";
import { getUser, saveUser, editUser } from "./user.service";
import { withRouter } from "react-router-dom";
import DatePicker from "react-datepicker2";
import moment from "moment-jalaali";
import Toast from "../../components/toast";

class UserEdit extends Component {
  dateFormat = "jYYYY/jMM/jDD";
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
    getUser(id).then(res => {
      if (res.data.expiredDate !== null) {
        res.data.expiredDate = moment(res.data.expiredDate, this.dateFormat);
      }
      if (res.data.mobile != null) {
        res.data.mobile = res.data.mobile.toString();
      }
      this.setState(res.data);
    });
  };

  newState = () => {
    return {
      id: "",
      loginName: "",
      email: "",
      expiredDate: moment().add(30, "days"),
      mobile: "",
      status: 1,
      userName: "",
      forceUpdate: true
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
    if (entity.expiredDate !== null) {
      entity.expiredDate = entity.expiredDate.format(this.dateFormat);
    }
    this.state.id === ""
      ? saveUser(entity)
          .then(res => {
            if (entity.expiredDate !== null) {
              this.setState({
                expiredDate: moment(entity.expiredDate, this.dateFormat)
              });
            }
            this.setState({
              id: res.data.id
            });
            Toast.showMessage({
              customMsg: "رمز عبور تعیین شده " + res.data.password + " میباشد.",
              options: {
                autoClose: false,
                closeOnClick: false
              }
            });
            this.props.history.push("edit/" + res.data.id);
          })
          .catch(() => {
            if (entity.expiredDate !== null) {
              this.setState({
                expiredDate: moment(entity.expiredDate, this.dateFormat)
              });
            }
          })
      : editUser(entity).finally(() => {
          if (entity.expiredDate !== null) {
            this.setState({
              expiredDate: moment(entity.expiredDate, this.dateFormat)
            });
          }
        });
  };

  render() {
    let title = this.state.id === "" ? "ثبت" : "ویرایش";
    return (
      <div className="container-fluid">
        <Card title={title + " کاربر"}>
          <input type="hidden" value={this.state.id} />
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">
              نام کاربری
            </label>
            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.loginName}
              disabled={this.state.id !== ""}
              onChange={e => this.setState({ loginName: e.target.value })}
            />
            {this.validator.message(
              "نام کاربری",
              this.state.loginName,
              "required|username|min:5|max:16"
            )}
          </div>
          {/* <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2"></label>
            <span className="hint">حروف انگلیسی و کاراکتر _ مجاز میباشد</span>
          </div> */}
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">
              نام و نام خانوادگی
            </label>
            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.userName}
              onChange={e => this.setState({ userName: e.target.value })}
            />
            {this.validator.message(
              "نام و نام خانوادگی",
              this.state.userName,
              "required|max:50"
            )}
          </div>

          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2">موبایل</label>
            <input
              type="text"
              maxLength={10}
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.mobile || ""}
              onChange={e => this.setState({ mobile: e.target.value })}
            />
            {this.validator.message(
              "موبایل",
              this.state.mobile,
              "numeric|min:10|max:10"
            )}
          </div>

          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2">ایمیل</label>
            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.email || ""}
              onChange={e => this.setState({ email: e.target.value })}
            />
            {this.validator.message("ایمیل", this.state.email, "max:45|email")}
          </div>

          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">
              تاریخ انقضاء حساب
            </label>
            <div className="col-sm-8 col-md-6 col-lg-4 pr-0">
              <div className="input-group">
                <DatePicker
                  inputReadOnly
                  ref={c => (this._calendar = c)}
                  isGregorian={false}
                  timePicker={false}
                  min={moment().subtract(1, "days")}
                  className="form-control"
                  inputJalaaliFormat={this.dateFormat}
                  onChange={value => this.setState({ expiredDate: value })}
                  value={this.state.expiredDate}
                />
                <div
                  className="input-group-append"
                  onClick={() => {
                    this._calendar.setOpen(true);
                  }}
                >
                  <span className="input-group-text">
                    <span className="fa fa-calendar"></span>
                  </span>
                </div>
                {this.validator.message(
                  "تاریخ انقضاء حساب",
                  this.state.expiredDate,
                  "required"
                )}
              </div>
            </div>
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

export default withRouter(UserEdit);
