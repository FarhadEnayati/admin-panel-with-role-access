import React, { Component } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card";
import Grid from "../../components/grid";
import { listRoleUser } from "./role.user.service";

class RoleUserList extends Component {
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
    let rows = await listRoleUser(this.props.url);
    this.setState({ rows: rows.data, loading: false });
  }
  getData = async () => {
    let data = {
      columns: [
        {
          label: "شناسه کاربر",
          name: "userId",
          width: "60px",
          class: "en",
          match: "equal"
        },
        {
          label: "نام کاربر",
          name: "loginName",
          class: "en",
          width: "150px"
        },
        {
          label: "نام و نام‌خانوادگی کاربر",
          name: "userName",
          width: "150px"
        },
        {
          label: "شناسه نقش",
          name: "roleId",
          width: "60px",
          class: "en",
          match: "equal"
        },
        {
          label: "نام نقش",
          name: "roleName",
          width: "150px"
        },
        {
          label: "وضعیت",
          name: "status",
          type: "boolean",
          width: "12%",
          mapper: value => {
            switch (Number.parseInt(value)) {
              case 0:
                return "غیرفعال";
              case 1:
                return "فعال";
              default:
                return "";
            }
          },
          search: [
            {
              key: 0,
              value: "غیرفعال"
            },
            {
              key: 1,
              value: "فعال"
            }
          ]
        }
      ],
      operations: [
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
  render() {
    return (
      <div className="container-fluid">
        <Card lov={this.props.lov} icon="fa fa-plus" title="تخصیص نقش به کاربر">
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

export default RoleUserList;
