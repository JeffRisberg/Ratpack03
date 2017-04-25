import React from "react";
import {Route, IndexRoute} from "react-router";
import AppRoot from "./containers/AppRoot";
import Home from "./components/Home";
import UserList from "./components/Users/UserList";
import UserDetail from "./components/Users/UserDetail";

export default (
    <Route path="/" component={AppRoot}>
        <IndexRoute component={Home}/>
        <Route path="users">
            <IndexRoute component={UserList}/>
            <Route path="detail/:id" component={UserDetail}/>
            <Route path="create" component={UserDetail}/>
        </Route>
    </Route>
);
