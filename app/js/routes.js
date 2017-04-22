import React from "react";
import {Route, IndexRoute} from "react-router";
import AppRoot from "./containers/AppRoot";
import Home from "./components/Home";
import UserPage from "./containers/UserPage";
import UserDetail from "./components/Users/UserDetail";

export default (
    <Route path="/" component={AppRoot}>
        <IndexRoute component={Home}/>
        <Route path="users">
            <IndexRoute component={UserPage}/>
            <Route path="detail/:id" component={UserDetail}/>
        </Route>
    </Route>
);
