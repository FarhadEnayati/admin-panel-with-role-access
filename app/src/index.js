import "react-app-polyfill/ie9";
import "react-app-polyfill/ie11";
import "react-app-polyfill/stable";

import "bootstrap-v4-rtl/dist/css/bootstrap-rtl.min.css";
import "font-awesome/css/font-awesome.css";
import "react-toastify/dist/ReactToastify.css";
import "./css/toastify.css";
import "./css/index.css";
import "./css/calendar.css";
import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch, Redirect, Link } from "react-router-dom";
import { ToastContainer, ToastPosition, cssTransition } from "react-toastify";
import App from "./App";
import Login from "./components/page/login";
import NotFound from "./components/page/not-found";
import Logout from "./components/logout";
import Storage from "./components/storage";
import * as serviceWorker from "./serviceWorker";
import packageJson from "../package.json";

const Flip = cssTransition({
  enter: "Toastify__flip-enter",
  exit: "Toastify__flip-exit",
  duration: [750, 0]
});
ReactDOM.render(
  <div className="rtl">
    <ToastContainer
      rtl
      position={ToastPosition.BOTTOM_LEFT}
      autoClose={15000}
      transition={Flip}
    />
    <BrowserRouter basename={packageJson.homepage + "/"}>
      <div id="header-container">
        <Link to="/">
          <div id="header"></div>
        </Link>
      </div>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/logout" component={Logout} />
        <Route path="/not-found" component={NotFound} />
        <Route
          path="/"
          render={() => {
            let token = Storage.getItem("token");
            let role = Storage.getItem("role");
            if (token && role) {
              return <App />;
            } else {
              return <Redirect to="/login" />;
            }
          }}
        />
        <Redirect to="/login" />
      </Switch>
    </BrowserRouter>
  </div>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
