import { Switch, Route } from "react-router-dom";
import React from "react";
import RoleEdit from "./role.edit";
import RoleList from "./role.list";

const RoleRoutes = ({ match, validator }) => {
  return (
    <Switch>
      <Route path={`${match.url}`} exact component={RoleList} />
      <Route
        path={`${match.url}/add/`}
        exact
        render={props => <RoleEdit {...props} validator={validator} />}
      />
      <Route
        path={`${match.url}/edit/:id`}
        exact
        render={props => <RoleEdit {...props} validator={validator} />}
      />
    </Switch>
  );
};

export default RoleRoutes;
