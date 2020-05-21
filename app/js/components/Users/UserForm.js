import React from 'react'
import {connect} from 'react-redux';
import {clearForm, handleFormFieldChange, setForm} from '../../actions/forms';
import "./Users.scss";

const formName = 'userForm';

/**
 * User Editing Form
 *
 * @author Jeff Risberg, Brandon Risberg
 * @since May 2016
 */
class UserForm extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.props.setForm(formName, this.props.formData)
  }

  componentWillUnmount() {
    this.props.clearForm(formName)
  }

  render() {
    if (this.props.form != null && this.props.form !== undefined) {

      return (
          <div className={this.props.className}>
            <form onSubmit={(e) => {
              this.props.handleSubmit(e, this.props.form)
            }}>

              <p>Email:</p>
              <p>
                <input type="text" name="email" size="40"
                       defaultValue={this.props.form.email}
                       onChange={(e) => {
                         this.props.handleFormFieldChange(formName, e)
                       }}/>
              </p>

              <p>First Name:</p>
              <p>
                <input type="text" name="firstname" size="30"
                       defaultValue={this.props.form.firstname}
                       onChange={(e) => {
                         this.props.handleFormFieldChange(formName, e)
                       }}/>
              </p>

              <p>Last Name:</p>
              <p>
                <input type="text" name="lastname" size="30"
                       defaultValue={this.props.form.lastname}
                       onChange={(e) => {
                         this.props.handleFormFieldChange(formName, e)
                       }}/>
              </p>

              <p>City:</p>
              <p>
                <input type="text" name="city" size="40"
                       defaultValue={this.props.form.city}
                       onChange={(e) => {
                         this.props.handleFormFieldChange(formName, e)
                       }}/>
              </p>

              <p>State:</p>
              <p>
                <input type="text" name="state" size="20"
                       defaultValue={this.props.form.state}
                       onChange={(e) => {
                         this.props.handleFormFieldChange(formName, e)
                       }}/>
              </p>

              <div>
                <input type="submit" value="Submit"
                       className="btn btn-default"/>
              </div>
              <div>
                <a onClick={(e) => this.props.handleDelete(e, this.props.form)}
                   className="btn btn-default">
                  Delete
                </a>
              </div>
            </form>
          </div>
      );
    } else {
      return null;
    }
  }
}

const mapStateToProps = (state) => {
  return {
    form: state.forms[formName]
  };
};
export default connect(
    mapStateToProps,
    {setForm, handleFormFieldChange, clearForm}
)(UserForm);
