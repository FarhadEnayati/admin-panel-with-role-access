import React, { Component } from "react";
import PropTypes from "prop-types";

class Uploader extends Component {
  constructor(props) {
    super(props);
    this.state = {
      originalName: "انتخاب فایل",
      key: this.props.date
    };
  }
  setOriginalName(e) {
    let originalName = e.target.value.split("\\").pop();
    this.setState({ originalName });
  }
  render() {
    return (
      <form
        className={this.props.className}
        style={{ paddingRight: 0, paddingLeft: 0 }}
      >
        <div className="custom-file">
          <input
            id={this.props.id}
            className={"custom-file-input "}
            type="file"
            onChange={e => {
              if (!e.target.value) return;
              this.props.onChange(e);
              this.setOriginalName(e);
            }}
            disabled={this.props.disabled}
            accept={this.props.accept != null ? this.props.accept.join() : null}
            key={this.state.key}
          />
          <label className="custom-file-label">{this.state.originalName}</label>
        </div>
      </form>
    );
  }
}
Uploader.propTypes = {
  accept: PropTypes.array,
  id: PropTypes.string,
  disabled: PropTypes.bool.isRequired,
  onChange: PropTypes.func.isRequired
};
export default Uploader;
