import React, {Component} from 'react';
import {connect} from 'react-redux';
import {queryUsers} from '../../actions/users';
import {UserListComponent} from '../Users';
import './Users.scss';

class UserListContainer extends Component {

  componentDidMount() {
    this.props.queryUsers();
  }

  render() {
    if (this.props.users != undefined) {
      return (
          <div className="userPage">
            <UserListComponent
                records={this.props.users}
                status={this.props.status}/>
          </div>
      );
    } else {
      return null;
    }
  }
}

const mapStateToProps = (state) => ({
  users: state.app.users.data,
  status: {
    isFetching: state.app.users.isFetching,
    ...state.app.appErrors,
  },
});

export default connect(
    mapStateToProps,
    {queryUsers}
)(UserListContainer);


