import { Switch, Route } from "react-router-dom";
import React from "react";
import UserEdit from "./user.edit";
import UserList from "./user.list";

const UserRoutes = ({ match, validator }) => {
  return (
    <Switch>
      <Route path={`${match.url}`} exact component={UserList} />
      <Route
        path={`${match.url}/add/`}
        render={props => <UserEdit {...props} validator={validator} />}
        exact
      />
      <Route
        path={`${match.url}/edit/:id`}
        render={props => <UserEdit {...props} validator={validator} />}
        exact
      />
    </Switch>
  );
};

export default UserRoutes;
