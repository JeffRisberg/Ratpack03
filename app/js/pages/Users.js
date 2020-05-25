import React, {Component} from 'react';
import UserListContainer from '../components/Users/UserListContainer';

class Users extends Component {

  render() {
    const id = this.props.match.params != undefined ? this.props.match.params.id
        : undefined;
    const content = (id != undefined) ?
        <UserFormContainer {...this.props} /> :
        <UserListContainer {...this.props} />;

    return (
        <div>
          {content}
        </div>
    );
  }
}

export default Users;
