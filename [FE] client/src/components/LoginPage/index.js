
import React, { Component } from 'react';
import './loginStyles.css'
import { assets } from '../../constants/assetsImage';
import { connect } from "react-redux";
import { compose } from "redux";
import { withTranslation } from "react-i18next";
// @Actions
import AuthorizationActions from '../../redux/actions/auth'
// @Components
import ForgotPassword from './ForgotPass';
import { toastError } from '../../utils/toastHelper';
// @Constance
import brandpng from '../../img/identification-documents.png'
import bgsvg from '../../img/brand.png'
class LoginPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			phonenumber: '',
			password: '',
		}
	}

	onLogin = () =>{
		const { phonenumber, password } = this.state;
		const { onLogin, t } = this.props;
		const data = {
			phonenumber, 
			password
		};
		if(phonenumber && password){
			onLogin(data);
		}
		else{
			toastError(`${t('login.toastify.error')}`)
		}
	}
	onSubmit = (event) =>{
    event.preventDefault();
  }

	onChange = (event) =>{
    var target=event.target;
    var name=target.name;
    var value=target.value;
    this.setState({
      [name]:  value
    })
	}

	componentDidMount(){ 
		document.title = "[ApumStore] Đăng nhập"
		this.improveScreen()
	}

	UNSAFE_componentWillReceiveProps(props){
		const { loggedIn, history } = props;
		if(loggedIn && loggedIn===true){
			history.push('/');
		}
	}

	improveScreen() {
		const inputs = document.querySelectorAll(".input");
		function addcl(){
			let parent = this.parentNode.parentNode;
			parent.classList.add("focus");
		}

		function remcl(){
			let parent = this.parentNode.parentNode;
			if(this.value === ""){
				parent.classList.remove("focus");
			}
		}
		inputs.forEach(input => {
			input.addEventListener("focus", addcl);
			input.addEventListener("blur", remcl);
		});
	}

	render() {
		const { t } = this.props;
		const { phonenumber, password } = this.state;
		return (
			<div>
				<img className="wave" src={ assets("wave.png")} alt="" />
				<div className="container_login">
				<div className="img">
						<img src={ bgsvg} alt="" />
					</div>
					<div className="login-content">
						<form>
							<h2 className="title">Trang đăng nhập khách hàng</h2>
							<img className="pb-4" src={brandpng} alt=""></img>
							<div className="input-div one">
								<div className="i">
									<i className="fas fa-user" />
								</div>
								<div className="div">
									<h5>Số điện thoại</h5>
									<input type="phonenumber" className="input" name="phonenumber" value={phonenumber} onChange={this.onChange}/>
								</div>
							</div>
							<div className="input-div pass">
								<div className="i">
									<i className="fas fa-lock" />
								</div>
								<div className="div">
									<h5>Mật khẩu</h5>
									<input type="password" className="input" name="password" value={password} onChange={this.onChange}/>
								</div>
							</div>
							<div onClick={()=> this.onForgotPass} className="text-right" data-bs-toggle="modal" data-bs-target="#forgotPassword">Quên mật khẩu?</div>
							<div className="row">
								<div className="col-12">
									<button type="button" className="btn" value="Đăng nhập" onClick={()=> this.onLogin()}>{t('header.login.button')}</button>
								</div>
								<div className="col-12">
									<form action="/user/dang-ky">
										<button type="submit" className="btn" value="Đăng ký">{t('header.signup.button')}</button>
									</form>
								</div>
							</div>
						</form>
						<ForgotPassword/>
					</div>
				</div>
				
			</div>
		);
	}
}

const mapStateToProps = (state) => {
  return {
		loggedIn: state.auth.loggedIn
  };
};

const mapDispatchToProps =(dispatch)=> {
	return {
		onLogin : (data) =>{
			dispatch(AuthorizationActions.onLogin(data))
		}
	}
};

const withConnect = connect(mapStateToProps, mapDispatchToProps);
export default compose(withConnect, withTranslation())(LoginPage);
