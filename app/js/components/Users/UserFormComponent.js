/*eslint no-class-assign: 0*/
import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Field } from 'redux-form';
import './Users.scss';

/**
 * User Editing Form
 *
 * @author Jeff Risberg
 * @since May 2017
 */

// eslint-disable-next-line
export const renderField = ({ input, label, type, meta: { touched, error }, size }) => {
  const className = (touched && error) ? 'form-input form-input-error' : 'form-input';
  return (
    <div>
      <input {...input} size={size || 20} type={type} className={className} autoComplete="off"/>
      {touched && ((error && <span className="form-input-error-copy">{error}</span>))}
    </div>
  );
};

class UserFormComponent extends Component {
  static propTypes = {
    handleSubmit: PropTypes.func.isRequired,
    reset: PropTypes.func.isRequired,
    submitHandler: PropTypes.func.isRequired,
    submitting: PropTypes.bool,
    submitSucceeded: PropTypes.bool,
    error: PropTypes.string,
  };

  static defaultProps = {
    submitting: false,
    submitSucceeded: false,
    error: '',
  };

  componentDidMount() {
    this.props.fetchHandler(this.props.match.params.id);
  }

  componentWillUnmount() {
    this.props.reset();
  }

  render() {
    const messageClass = this.props.error ? 'form-error-copy' : 'form-label';

    return (
      <div className="users__detail">
        <form onSubmit={this.props.handleSubmit(this.props.submitHandler)}>
          {(this.props.error) ? <div className={messageClass}>{this.props.error}</div> : null }
          <div>
            <label>Email:</label>
            <div>
              <Field name="email" size="40"
                component={renderField} type="text" placeholder=""/>
            </div>
          </div>
          <div>
            <label>First Name:</label>
            <div>
              <Field name="firstName" size="40"
                component={renderField} type="text" placeholder=""/>
            </div>
          </div>
          <div>
            <label>Last Name:</label>
            <div>
              <Field name="lastName" size="40"
                     component={renderField} type="text" placeholder=""/>
            </div>
          </div>
          <div>
            <button type="submit" className="btn btn-default">Submit</button>
          </div>
          <div>
            <button onClick={(e) => this.props.deleteHandler(e, this.props.match.params.id)}
              className="btn btn-default">
              Delete
            </button>
          </div>
        </form>
      </div>
    );
  }
}

export default UserFormComponent;

