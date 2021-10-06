import { Switch, Route } from "react-router-dom";
import React from "react";
import RoleUserEdit from "./role.user.edit";
import RoleUserList from "./role.user.list";

const RoleUserRoutes = ({ match, validator }) => {
  return (
    <Switch>
      <Route path={`${match.url}`} exact component={RoleUserList} />
      <Route
        path={`${match.url}/add/`}
        exact
        render={props => <RoleUserEdit {...props} validator={validator} />}
      />
      <Route
        path={`${match.url}/edit/:id`}
        exact
        render={props => <RoleUserEdit {...props} validator={validator} />}
      />
    </Switch>
  );
};

export default RoleUserRoutes;
