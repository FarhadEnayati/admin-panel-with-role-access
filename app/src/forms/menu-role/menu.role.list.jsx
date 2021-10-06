import React, { Component } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card";
import Grid from "../../components/grid";
import { listMenuRole } from "./menu.role.service";

class MenuRoleList extends Component {
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
    let rows = await listMenuRole(this.props.url);
    this.setState({ rows: rows.data, loading: false });
  }
  getData = async () => {
    let data = {
      columns: [
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
          label: "شناسه منو",
          name: "menuId",
          width: "60px",
          class: "en",
          match: "equal"
        },
        {
          label: "نام منو",
          name: "menuName",
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
        <Card lov={this.props.lov} icon="fa fa-plus" title="تخصیص منو به نقش">
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

export default MenuRoleList;
