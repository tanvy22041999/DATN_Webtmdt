import React, { Component } from 'react';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { withTranslation } from 'react-i18next'
import { toastError } from '../../utils/toastHelper';
// @Actions
import AuthorizationActions from '../../redux/actions/auth'

class ForgotPassword extends Component {
  constructor(props) {
    super(props);
    this.state = {
      phonenumber: ""
    }
  }

  onChange = (event) => {
    var target = event.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      [name]: value
    })
  }

  sendEmail() {
    const { phonenumber } = this.state;
    const { onForgotPassword, t } = this.props
    if(phonenumber) {
      onForgotPassword({phonenumber});
    }
    else{
      toastError(`${t('login.toastify.error.1')}`)
    }
  }

  render() {
    const { t } = this.props;
    const { phonenumber } = this.state;
    return (  
      <div show="true" className="modal fade" id="forgotPassword" role="dialog" >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Quên mật khẩu</h5>
              <button type="button" className="close" data-bs-dismiss="modal">&times;</button>
            </div>
            <div className="modal-body">
              <div className="form-group">
                <label>Nhập số điện thoại</label>
                <input type="phonenumber" className="form-control" name="phonenumber" value={phonenumber} onChange={this.onChange}/>
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-success" data-bs-dismiss="modal" onClick={() => this.sendEmail()}>Lấy mã xác nhận</button>
            </div>
          </div> 
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) =>{
  return {

  }
}

const mapDispatchToProps =(dispatch)=> {
  return {
    onForgotPassword : (data) =>{
			dispatch(AuthorizationActions.onForgotPassword(data))
		},
	}
};

const withConnect = connect(mapStateToProps, mapDispatchToProps)

export default compose(
  withConnect,
  withTranslation()
)(ForgotPassword);