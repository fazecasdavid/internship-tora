import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {bindActionCreators} from 'redux'
import {actionCreators} from "../state/index"
import httpService from "../services/httpService";
import loginService from "../services/loginService";


function LoginWindow() {
    const state = useSelector((state) => state)
    const dispatch = useDispatch();
    const {setUserName} = bindActionCreators(actionCreators, dispatch);

    const [name, setName] = useState('');
    const [password, setPassword] = useState('');


    const onChangeName = (event) => {
        setName(event.target.value)
    }

    const onChangePassword = (event) => {
        setPassword(event.target.value)
    }

    const onClickLogin = async () => {
        await loginService.getAndSetToken(name, password)?.catch(console.error)
        await httpService.handleLogin(name)?.catch(console.error);
        setUserName(name)
        console.log(state)
    }

    return (
        <div className='action'>
            <label>Login</label>
            <input placeholder='Type your user' onChange={onChangeName}/><br/>
            <input type="password" placeholder='Type your password' onChange={onChangePassword}/><br/>
            <div><input type='button' onClick={onClickLogin} value='Login'/></div>

        </div>
    )
}

export default LoginWindow
