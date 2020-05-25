import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import {NavLink, Route, Router, Switch} from 'react-router-dom';
import Home from './pages/Home';
import Users from './pages/Users';

class AppRoot extends Component {
  static propTypes = {
    history: PropTypes.object.isRequired
  };

  render() {
    const {history} = this.props;

    return (
      <Router history={history}>
        <div>
          <div className="navbar navbar-inverse navbar-fixed-top" style={{marginBottom: '0px'}}>
            <div className="container">
              <div className="navbar-inner">
                <NavLink to="/" className="navbar-brand">Ratpack03</NavLink>

                <div className="navbar-collapse collapse">
                  <ul className="nav navbar-nav">
                    <li><NavLink to="/users">Users</NavLink></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div className="container">
            <Switch>
              <Route exact path="/" component={Home}/>
              <Route exact path="/users" component={Users}/>
              <Route exact path="/users/detail/:id" component={Users}/>
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default AppRoot;
