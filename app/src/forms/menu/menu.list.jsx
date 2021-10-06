import React, { Component } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card";
import Grid from "../../components/grid";
import { listMenu } from "./menu.service";

class MenuList extends Component {
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
    let rows = await listMenu(this.props.url);
    this.setState({ rows: rows.data, loading: false });
  }
  getData = async () => {
    let data = {
      columns: [
        {
          label: "نام منو",
          name: "name",
          width: "180px"
        },
        {
          label: "نام منوی اصلی",
          name: "parentName",
          width: "120px"
        },
        {
          label: "آیکون",
          name: "icon",
          class: "en",
          width: "120px"
        },
        {
          label: "ترتیب",
          name: "order",
          class: "en",
          width: "65px"
        },
        {
          label: "مسیر کنترل",
          name: "path",
          class: "en",
          width: "170px"
        },
        {
          label: "نوع",
          name: "type",
          width: "100px",
          type: "boolean",
          mapper: value => {
            switch (Number.parseInt(value)) {
              case 1:
                return "منوی اصلی";
              case 2:
                return "زیر منو";
              default:
                return "";
            }
          },
          search: [
            { key: 1, value: "منوی اصلی" },
            { key: 2, value: "زیر منو" }
          ]
        },
        {
          label: "وضعیت",
          name: "status",
          type: "boolean",
          width: "100px",
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
        <Card lov={this.props.lov} icon="fa fa-plus" title="منوهای سیستم">
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

export default MenuList;
