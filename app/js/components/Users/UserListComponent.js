import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link } from 'react-router-dom';
import Loading from '../Loading';
import '../Loading/Loading.scss';
import './Users.scss';

class UserListComponent extends Component {
  static propTypes = {
    records: PropTypes.array.isRequired,
    status: PropTypes.object.isRequired
  };

  render() {
    const { records, status } = this.props;

    if (status.isFetching) {
      return (
        <div className="users__list">
          <Loading size="large" color="purple"/>
        </div>
      );
    }

    const userNodes = records.map((user, key) => {
      const id = user.id;

      return (
        <tr key={key}>
          <td><Link to={'/users/detail/' + id} className='btn btn-default'>View</Link></td>
          <td>{user.email}</td>
          <td>{user.firstName}</td>
          <td>{user.lastName}</td>
          <td>{user.city}</td>
          <td>{user.state}</td>
        </tr>
      );
    });

    return (
      <div className="users__list">
        <table className="table">
          <thead>
            <tr>
              <th>{''}</th>
              <th>Email</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>City</th>
              <th>State</th>
            </tr>
          </thead>
          <tbody>
            {userNodes}
          </tbody>
        </table>
      </div>
    );
  }
}

export default UserListComponent;
