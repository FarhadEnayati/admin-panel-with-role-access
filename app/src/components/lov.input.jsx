import React, { Component } from "react";

class LovInput extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: props.id,
      name: props.name
    };
  }
  render() {
    return (
      <React.Fragment>
        {this.props.id !== undefined ? (
          <input
            type="text"
            className="form-control col-2"
            disabled
            value={this.props.id || ""}
          />
        ) : null}

        <input
          type="text"
          className="form-control"
          disabled
          value={this.props.name || ""}
        />

        <div className="input-group-append">
          <span className="input-group-text" onClick={this.props.clear}>
            <span className="fa fa-times"></span>
          </span>
          <span className="input-group-text" onClick={this.props.select}>
            <span className="fa fa-plus"></span>
          </span>
        </div>
      </React.Fragment>
    );
  }
}

export default LovInput;
