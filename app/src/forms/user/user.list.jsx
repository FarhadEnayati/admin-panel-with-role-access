import React, { Component } from "react";
import { Link } from "react-router-dom";

import Grid from "../../components/grid";
import { listUser } from "./user.service";
import Card from "./../../components/card";
import { resetPassword } from "./user.service";
import Toast from "../../components/toast";
import { confirm } from "../../components/confirmation";

class UserList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      rows: [],
      loading: true
    };
  }
  async componentDidMount() {
    let data = await this.getData();
    this.setState({ data: data });
    let rows = await listUser(this.props.url);
    this.setState({ rows: rows.data, data: data, loading: false });
  }
  getData = async () => {
    let data = {
      columns: [
        {
          label: "شناسه کاربری",
          name: "id",
          width: "80px",
          class: "en",
          match: "equal"
        },
        {
          label: "نام کاربری",
          name: "loginName",
          class: "en",
          width: "80px"
        },
        {
          label: "نام و نام خانوادگی",
          name: "userName",
          width: "14%"
        },
        {
          label: "موبایل",
          name: "mobile",
          class: "en",
          width: "11%"
          // type: "number"
          // match: "equal"
        },
        {
          label: "ایمیل",
          name: "email",
          class: "en",
          width: "150px"
        },
        {
          label: "انقضاء پسورد",
          name: "passwordExpiredDate",
          class: "en",
          width: "10%"
          // type: "gregorian"
        },
        {
          label: "انقضاء حساب",
          name: "expiredDate",
          class: "en",
          width: "10%"
        },
        {
          label: "وضعیت",
          name: "status",
          type: "boolean",
          width: "60px",
          mapper: value => {
            switch (Number.parseInt(value)) {
              case 0:
                return "غیرفعال";
              case 1:
                return "فعال";
              default:
                return "";
            }
          }
        }
      ],
      operations: [
        {
          name: "بازنشانی رمز",
          class: "btn-danger btn-w-1",
          click: this.reset
        },
        {
          name: "ویرایش",
          class: "btn-secondary",
          click: this.editRecord
        }
      ],
      doubleClick: this.editRecord
    };
    return data;
  };
  editRecord = r => {
    this.props.history.push(this.props.match.url + "/edit/" + r["id"]);
  };
  reset = async r => {
    if (
      await confirm(
        "آیا از تغییر رمز عبور کاربر " + r["loginName"] + " مطمئن هستید؟"
      )
    ) {
      resetPassword(r["id"]).then(res => {
        Toast.showMessage({
          customMsg: "رمز عبور تعیین شده " + res.data.password + " میباشد.",
          options: {
            autoClose: false,
            closeOnClick: false
          }
        });
      });
    }
  };
  render() {
    return (
      <div className="container-fluid">
        <Card lov={this.props.lov} icon="fa fa-plus" title="کاربران سیستم">
          {!this.props.lov && (
            <div className="mb-3">
              <Link className="btn btn-bpm" to={`${this.props.match.url}/add`}>
                جدید
              </Link>
            </div>
          )}

          <Grid
            data={this.state.data}
            rows={this.state.rows}
            loading={this.state.loading}
            props={this.props}
          />
        </Card>
      </div>
    );
  }
}

export default UserList;
