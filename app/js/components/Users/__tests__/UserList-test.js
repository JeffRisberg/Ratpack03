jest
.unmock('redux')
.unmock('react-redux')
.unmock('../UserList')
.unmock('../../../../js/reducers/users')
;

import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';

import {combineReducers, createStore} from 'redux';
import {Provider} from 'react-redux';

import {types} from '../../../../js/types'

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
      users: [{firstname: "Jack", lastname: "Smith"}]
    });

    const userList =
        TestUtils.renderIntoDocument(
            <div>
              <Provider store={store}>
                <UserList/>
              </Provider>
            </div>
        );

    const userListNode = ReactDOM.findDOMNode(userList);

    expect(userListNode.textContent).toContain('Jack'); // in user firstname
    expect(userListNode.textContent).toContain('Smith'); // in user lastnae
  });
});
