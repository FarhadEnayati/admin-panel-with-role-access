import React, { Component } from "react";
import { Route, Switch, Redirect } from "react-router-dom";
import Navbar from "./components/layout/navbar";
import Sidebar from "./components/layout/sidebar";
import Footer from "./components/layout/footer";
import Main from "./forms/main";
import ChangePassword from "./forms/change-pass";
import UserRoutes from "./forms/user/user.routes";
import RoleUserRoutes from "./forms/role-user/role.user.routes";
import SimpleReactValidator from "simple-react-validator";
import "simple-react-validator/dist/locale/fa";
import RoleRoutes from "./forms/role/role.routes";
import MenuRoutes from "./forms/menu/menu.routes";
import MenuRoleRoutes from "./forms/menu-role/menu.role.routes";

class App extends Component {
  render() {
    const validator = new SimpleReactValidator({
      validators: {
        username: {
          message: ":attribute باید شامل حروف انگلیسی یا کاراکتر _ باشد.",
          rule: (val, params, validator) =>
            validator.helpers.testRegex(val, /^[A-Z_]*$/i)
        },
        end_url: {
          message:
            ":attribute باید شامل حروف کوچک انگلیسی یا کاراکتر - یا / باشد.",
          rule: (val, params, validator) =>
            validator.helpers.testRegex(val, /^[a-z-/]*$/)
        }
      },
      messages: {
        max: ":attribute باید حداکثر :max کاراکتر باشد.",
        min: ":attribute باید حداقل :min کاراکتر باشد.",
        required: ":attribute الزامی است.",
        email: "آدرس ایمیل باید معتبر باشد."
      },
      element: message => <label className="validate">{message}</label>,
      autoForceUpdate: this,
      locale: "fa"
    });
    return (
      <React.Fragment>
        <div id="cover-spin" style={{ display: "block" }}></div>
        <div className="rtl">
          <Navbar expiration={true} />
          <div className="wrapper">
            <Sidebar search={true} />
            <main
              role="main"
              className="col-md-9 ml-md-auto col-lg-10 pl-1 ml-auto"
            >
              <Switch>
                <Route path="/change-pass" component={ChangePassword} />
                <Route
                  path="/management/user"
                  render={props => (
                    <UserRoutes validator={validator} {...props} />
                  )}
                />
                <Route
                  path="/management/role"
                  render={props => (
                    <RoleRoutes validator={validator} {...props} />
                  )}
                />
                <Route
                  path="/management/role-user"
                  render={props => (
                    <RoleUserRoutes validator={validator} {...props} />
                  )}
                />
                <Route
                  path="/management/menu-role"
                  render={props => (
                    <MenuRoleRoutes validator={validator} {...props} />
                  )}
                />

                <Route
                  path="/management/menu"
                  render={props => (
                    <MenuRoutes validator={validator} {...props} />
                  )}
                />

                <Route path={"/"} exact component={Main} />
                <Redirect to="/not-found" />
              </Switch>
            </main>
          </div>
          <Footer />
        </div>
      </React.Fragment>
    );
  }
}

export default App;
