import { Switch, Route } from "react-router-dom";
import React from "react";

import MenuEdit from "./menu.edit";
import MenuList from "./menu.list";

const MenuRoutes = ({ match, validator }) => {
  return (
    <Switch>
      <Route path={`${match.url}`} exact component={MenuList} />
      <Route
        path={`${match.url}/add/`}
        exact
        render={props => <MenuEdit {...props} validator={validator} />}
      />
      <Route
        path={`${match.url}/edit/:id`}
        exact
        render={props => <MenuEdit {...props} validator={validator} />}
      />
    </Switch>
  );
};

export default MenuRoutes;
