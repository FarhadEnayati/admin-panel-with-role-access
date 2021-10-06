import React from "react";
import PropTypes from "prop-types";

import { Modal, ModalHeader, ModalBody } from "reactstrap";
import { confirmable, createConfirmation } from "react-confirm";

class Lov extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: true
    };
  }
  toggle = () => {
    this.setState({
      open: !this.state.open
    });
  };
  render() {
    const { Component, title, url, proceed } = this.props;
    return (
      <Modal
        isOpen={this.state.open}
        toggle={this.toggle}
        className="rtl"
        size="lg"
        style={{ width: "95%", maxWidth: "100%", marginTop: 0 }}
      >
        <ModalHeader toggle={this.toggle}>{title}</ModalHeader>
        <ModalBody>
          <div>
            <Component
              lov={true}
              lovDoubleClick={entity => {
                proceed(entity);
              }}
              url={url}
            />
          </div>
        </ModalBody>
      </Modal>
    );
  }
}

Lov.propTypes = {
  title: PropTypes.string.isRequired,
  url: PropTypes.string,
  Component: PropTypes.func.isRequired,
  proceed: PropTypes.func // called when ok button is clicked.
};

export function lov(Component, title, url, options = {}) {
  return createConfirmation(
    confirmable(Lov),
    0
  )({
    Component,
    title,
    url,
    ...options
  });
}
