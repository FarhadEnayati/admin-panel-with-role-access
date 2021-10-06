import React from "react";
import { Link } from "react-router-dom";
import "./../../css/not-found.css";

const NotFound = () => {
  return (
    <React.Fragment>
      <div id="notfound">
        <div className="notfound">
          <div className="notfound-404">
            <h1>404</h1>
          </div>
          <h2>صفحه مورد نظر پیدا نشد!</h2>

          <Link to="/">برگشت به صفحه اصلی</Link>
        </div>
      </div>
    </React.Fragment>
  );
};

export default NotFound;
