import fetch from "isomorphic-fetch";
import {push} from "react-router-redux";
import {initialize} from 'redux-form';
import {ActionTypes as types, forms} from '../constants';

export const queryUsers = () => {
  return function (dispatch) {

    dispatch({
      type: types.FETCH_USERS,
    });
    return fetch('/api/users', {})
    .then(response => response.json())
    .then((json) => {
      dispatch(
          {
            type: types.FETCH_USERS_SUCCESS,
            users: json.data
          }
      );
    });
  };
};

export const fetchUser = (id) => {
  return function (dispatch) {

    dispatch({
      type: types.FETCH_USERS,
    });
    return fetch('/api/users/' + id, {})
    .then(response => response.json())
    .then((json) => {
      dispatch(initialize(forms.User, json.data[0]));
      dispatch({
        type: types.FETCH_USERS_SUCCESS,
        users: json.data
      });
    });
  };
};

export const updateUser = (user) => {
  return function (dispatch) {

    return fetch('/api/users/' + user.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    })
    .then(response => response.json())
    .then((json) => {
      dispatch({
        type: types.PERSIST_USER_SUCCESS,
        users: json.data,
        meta: {
          log: ['user changed', user]
        }
      });
      dispatch(push('/users'));
    });
  };
};

export const deleteUser = (id) => {
  return function (dispatch) {

    return fetch('/api/users/' + id, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then(() => {
      dispatch(push('/users'));
    });
  };
};
