import React, { Component } from "react";
import Card from "../../components/card";
import { getRole, editRole, saveRole } from "./role.service";
import Toast from "../../components/toast";

class RoleEdit extends Component {
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
    getRole(id).then(res => {
      this.setState(res.data);
    });
  };
  newState = () => {
    return {
      id: "",
      name: "",
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
      ? saveRole(entity).then(res => {
          this.setState({
            id: res.data
          });
          Toast.showMessage({
            customMsg: "رکورد با موفقیت درج شد.",
            type: 4
          });
          this.props.history.push("edit/" + res.data);
        })
      : editRole(entity);
  };
  render() {
    let title = this.state.id === "" ? "ثبت" : "ویرایش";
    return (
      <div className="container-fluid">
        <Card title={title + " نقش"}>
          <div className="row">
            <label className="col-sm-4 col-md-3 col-lg-2 required">
              نام نقش
            </label>
            <input
              type="text"
              className="form-control col-sm-8 col-md-5 col-lg-4"
              value={this.state.name}
              onChange={e => this.setState({ name: e.target.value })}
            />
            {this.validator.message(
              "نام نقش",
              this.state.name,
              "required|max:50"
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

export default RoleEdit;
