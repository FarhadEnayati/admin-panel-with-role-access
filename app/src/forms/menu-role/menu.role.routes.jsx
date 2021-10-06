import { Switch, Route } from "react-router-dom";
import React from "react";
import MenuRoleEdit from "./menu.role.edit";
import MenuRoleList from "./menu.role.list";

const MenuRoleRoutes = ({ match, validator }) => {
  return (
    <Switch>
      <Route path={`${match.url}`} exact component={MenuRoleList} />
      <Route
        path={`${match.url}/add/`}
        exact
        render={props => <MenuRoleEdit {...props} validator={validator} />}
      />
      <Route
        path={`${match.url}/edit/:id`}
        exact
        render={props => <MenuRoleEdit {...props} validator={validator} />}
      />
    </Switch>
  );
};

export default MenuRoleRoutes;
