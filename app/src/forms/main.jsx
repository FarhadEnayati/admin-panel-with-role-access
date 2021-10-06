import React, { Component } from "react";
import { getDate } from "../services/common.service";
import Card from "../components/card";

class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      date: null
    };
  }
  async componentDidMount() {
    const { data } = await getDate();
    this.setState({
      date: data.date
    });
  }

  render() {
    return (
      <div className="container-fluid">
        <Card title="صفحه اصلی">
          <div className="row col-12">تاریخ امروز: {this.state.date}</div>
          <div className="row col-12">
            <h2>اطلاعیه ها:</h2>
          </div>
        </Card>
      </div>
    );
  }
}

export default Main;
