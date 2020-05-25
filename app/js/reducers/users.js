import {handleActions} from "redux-actions";
import {ActionTypes as types} from '../constants';

export default handleActions({
  [types.FETCH_USERS]: (state) => {
    return Object.assign({}, state, {
      data: [],
      isFetching: true,
    });
  },
  [types.FETCH_USERS_SUCCESS]: (state, action) => {
    return Object.assign({}, {data: action.users}, {
      isFetching: false,
    });
  },
}, {data: [], isFetching: false});
