jest
    .unmock('redux')
    .unmock('react-redux')
    .unmock('../UserList')
    .unmock('../../../../js/reducers/users')
;

import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';

import { createStore, combineReducers } from 'redux';
import { Provider } from 'react-redux';

import { types } from '../../../../js/types'

import UserList from '../../../../js/components/Users/UserList';

import users from '../../../../js/reducers/users';

describe('We can render an UserList component', () => {
    it('contains content', () => {

        const combinedReducers = combineReducers({
            users
        });

        const initialContent = {
            users: {idList: [], records: {}}
        };

        const store = createStore(
            combinedReducers,
            initialContent
        );

        store.dispatch({
            type: types.APPEND_USERS,
            users: [{text: "Dinner", time: "1800"}]
        });

        const userList =
            TestUtils.renderIntoDocument(
                <div>
                    <Provider store={store}>
                        <UserList />
                    </Provider>
                </div>
            );

        const userListNode = ReactDOM.findDOMNode(userList);

        //expect(userListNode.textContent).toContain('hours'); // in table header

        //expect(userListNode.textContent).toContain('Dinner'); // in user text
        //expect(userListNode.textContent).toContain('1800'); // in user time
    });
});