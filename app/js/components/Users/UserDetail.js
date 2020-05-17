import React from "react";
import {connect} from "react-redux";
import {fetchUser, saveUser, addUser, deleteUser} from "../../actions/users";
import UserForm from "./UserForm";
import "./Users.scss";

class UserDetail extends React.Component {

    componentDidMount() {
        const id = this.props.params.id;

        if (id !== null && id !== undefined) {
            const user = this.props.users.records[id];

            if (user == null)
                this.props.fetchUser(id);
        }
    }

    /*
    handleSubmit = (e, formData) => {
        e.preventDefault();

        const id = this.props.params.id;

        if (id !== null && id != undefined) {
            const user = this.props.users.records[this.props.params.id];

            user.email = formData.email;
            user.firstname = formData.firstname;
            user.lastname = formData.lastname;
            user.city = formData.city;
            user.state = formData.state;

            this.props.saveUser(user);

            this.context.router.push('/users');
        }
        else {
            const user = {};

            user.email = formData.email;
            user.firstname = formData.firstname;
            user.lastname = formData.lastname;
            user.city = formData.city;
            user.state = formData.state;

            this.props.addUser(user);

            this.context.router.push('/users');
        }
    }
     */

    /*
    handleDelete = (e) => {
        e.preventDefault();

        const user = this.props.users.records[this.props.params.id];

        this.props.deleteUser(user, '/users'); // this will go to /users after delete
    }
     */

    render() {
        const user = this.props.users.records[this.props.params.id];

        if (user != null) {
            return (
                <UserForm user={user} className="users__detail"
                          handleSubmit={this.handleSubmit}
                          handleDelete={this.handleDelete}
                          formData={{
                              email: user.email,
                              firstname: user.firstname,
                              lastname: user.lastname,
                              city: user.city,
                              state: user.state
                          }}/>
            );
        }
        else
            return (
                <UserForm user={user} className="users__detail"
                          handleSubmit={this.handleSubmit}
                          handleDelete={this.handleDelete}
                          formData={{
                              email: "",
                              firstname: "",
                              lastname: "",
                              city: "",
                              state: ""
                          }}/>
            );
    }
}
UserDetail.contextTypes = {
    router: React.PropTypes.object.isRequired
};

const mapStateToProps = (state) => {
    return {
        users: state.users
    };
};

export default connect(
    mapStateToProps,
    {fetchUser, saveUser, addUser, deleteUser}
)(UserDetail);
