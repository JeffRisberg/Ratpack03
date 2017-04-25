import React from "react";
import {Link} from "react-router";
import {connect} from "react-redux";
import {queryUsers} from "../../actions/users";
import "./Users.scss";

class UserList extends React.Component {

    componentDidMount() {
        this.props.queryUsers();
    }

    render() {
        const records = this.props.users.idList.map(id => this.props.users.records[id]);

        const userNodes = records.map((user, key) => {
            const id = user.id;

            var d = new Date(0); // The 0 there is the key, which sets the date to the epoch
            d.setUTCSeconds(user.lastUpdated / 1000);

            return (
                <tr key={key} className="users__user">
                    <td>
                        <Link to={'/users/detail/' + id} className='btn btn-default'>View</Link>
                    </td>
                    <td>
                        {user.email}
                    </td>
                    <td>
                        {user.firstname}
                    </td>
                    <td>
                        {user.lastname}
                    </td>
                    <td>
                        {user.city}
                    </td>
                    <td>
                        {user.state}
                    </td>
                    <td>
                        {d.toDateString()} {d.toTimeString()}
                    </td>
                </tr>
            );
        });

        return (
            <div>
                <Link className='btn btn-default' to='/users/create'>Create New User</Link>
                <table className="table users__list">
                    <tbody>
                    {userNodes}
                    </tbody>
                </table>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        users: state.users
    };
};
export default connect(
    mapStateToProps,
    {queryUsers}
)(UserList);

