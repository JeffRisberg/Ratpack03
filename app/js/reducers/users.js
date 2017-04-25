import {handleActions} from "redux-actions";
import {types} from "../types";

export default handleActions({
    [types.RESET_USERS]: (state, action) => {
        const idList = [];
        const records = {};

        action.users.forEach(record => {
            records[record.id] = record;
            idList.push(record.id);
        });

        return {idList, records};
    },

    [types.APPEND_USERS]: (state, action) => {
        const idList = state.idList;
        const records = state.records;

        action.users.forEach(record => {
            const id = record.id;

            if (idList.indexOf(id) < 0) idList.push(id);
            records[id] = record;
        });

        return {idList, records};
    }
}, {idList: [], records: {}});
