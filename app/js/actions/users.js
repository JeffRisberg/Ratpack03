import fetch from "isomorphic-fetch";
import {push} from "react-router-redux";
import {types} from "../types";

export const queryUsers = () => {
    return function (dispatch) {

        return fetch('/api/users', {})
            .then(response => response.json())
            .then((json) => {
                dispatch(
                    {
                        type: types.RESET_USERS,
                        users: json.data
                    });
            });
    };
};

export const fetchUser = (id) => {
    return function (dispatch) {

        return fetch('/api/users/' + id, {})
            .then(response => response.json())
            .then((json) => {
                dispatch(
                    {
                        type: types.APPEND_USERS,
                        users: json.data
                    });
            });
    };
};

export const saveUser = (user) => {
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
                    type: types.APPEND_USERS,
                    users: json.data
                });
            });
    };
};

export const addUser = (user) => {
    return function (dispatch) {

        return fetch("/api/users", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(response => response.json())
            .then((json) => {
                //dispatch({
                //    type: types.APPEND_USERS,
                //    users: json.data
                //});
            });
    }
};

export const deleteUser = (user, thenUrl) => {
    return function (dispatch) {

        return fetch('/api/users/' + user.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                dispatch(push(thenUrl))
            });
    };
};
