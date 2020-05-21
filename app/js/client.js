import React from "react";
import ReactDOM from "react-dom";
import {hashHistory, Router} from "react-router";
import {applyMiddleware, combineReducers, createStore} from "redux";
import {Provider} from "react-redux";
import {routerMiddleware, routerReducer} from "react-router-redux";
import thunkMiddleware from "redux-thunk";
import users from "./reducers/users";
import forms from "./reducers/forms";
import routes from "./routes";

const combinedReducers = combineReducers({
  users,
  forms,
  routing: routerReducer
});

const store = createStore(
    combinedReducers,
    {},
    applyMiddleware(routerMiddleware(hashHistory), thunkMiddleware)
);

ReactDOM.render(
    <Provider store={store}>
      <Router history={hashHistory} routes={routes}/>
    </Provider>,
    document.getElementById('app-root')
);
