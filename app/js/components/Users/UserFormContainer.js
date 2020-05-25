import { connect } from 'react-redux';
import { reduxForm } from 'redux-form';
import { forms } from '../../constants';
import { fetchUser, saveUser, deleteUser } from '../../actions/users';
import UserFormComponent from './UserFormComponent';
import './Users.scss';

const validate = (values) => {
  const errors = {};

  if (!values.firstName || values.firstName.trim().length < 4) {
    errors.name = 'Please enter a firstName of at least 4 characters.';
  }
  if (!values.lastName || values.lastName.trim().length < 4) {
    errors.name = 'Please enter a lastName of at least 4 characters.';
  }

  return errors;
};

const UserFormContainer = reduxForm({
  form: forms.User,
  validate,
})(UserFormComponent);

const mapStateToProps = state => ({
  initialValues: state.app.users.data,
  status: {
    isFetching: state.app.users.isFetching,
    ...state.app.appErrors,
  },
});

const mapDispatchToProps = dispatch => ({
  fetchHandler: (id) => {
    dispatch(fetchUser(id));
  },
  submitHandler: (values) => {
    const user = {
      ...values,
      name: values.name.trim(),
      value: values.value,
      description: values.description.trim()
    };
    dispatch(saveUser(user));
  },
  deleteHandler: (e, id) => {
    e.preventDefault();
    dispatch(deleteUser(id));
  },
});

const mergeProps = (stateProps, dispatchProps, ownProps) =>
  Object.assign({}, stateProps, dispatchProps, ownProps);

export default connect(mapStateToProps, mapDispatchToProps, mergeProps)(UserFormContainer);
