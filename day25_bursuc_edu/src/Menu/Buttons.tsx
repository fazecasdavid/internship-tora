import Button from './Button/Button';
import axios from "axios";
import './Buttons.css';
import { useSelector } from 'react-redux';


export default function Buttons() {
    const backendURL = 'http://34.207.124.142:8080/'
    const username = useSelector((state: any)=> state.username)
    const password = useSelector((state: any)=> state.password)
    const name = useSelector((state: any)=> state.name)
    const content = useSelector((state: any)=> state.content)

    const tokenUrl = "https://auth-dev-1.app.toradev.net/auth/realms/INTERNSHIP/protocol/openid-connect/token";



    function getAccessToken(_callback: any){
        return new Promise((resolve) =>{
            const params = new URLSearchParams();
            params.append('grant_type', 'password');
            params.append('client_id', 'chat-client');
            params.append('username', username);
            params.append('password', password);
            console.log(username);
            console.log(password);
            axios.post(tokenUrl, params,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }})
                .then(function (response) {
                    localStorage.removeItem("accessToken");   
                    localStorage.setItem("accessToken",response.data.access_token);  
                    console.log("Acquired token")
                    _callback();          
                })
                .catch(function (error) {
                    console.log(error);
                });
            });
    }


    function logIn(){
       getAccessToken(function(){
        console.log("Set username: "+username); 
        axios({
            method: 'put',
            url:  backendURL + 'username',
            data: username,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
       });
    }


    function handleHello() {
        console.log('Send hello to: ', name)
        axios({
            method: 'post',
            url: backendURL + 'hello',
            data: name,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleAck() {
        console.log('Send ack to: ', name)
        axios({
            method: 'post',
            url: backendURL + 'ack',
            data: name,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleSend() {
        let data = JSON.stringify({
            userToSend: name,
            content: content
        })
        console.log('Send message to: ', name)
        axios({
            method: 'post',
            url: backendURL + 'send',
            data: data,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleGroup() {
        let data = JSON.stringify({
            groupName: name,
            members: content
        })
        console.log('Send group ' + name)
        console.log(content)
        axios({
            method: 'post',
            url: backendURL + 'group',
            data: data,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleAckGroup() {
        console.log('Send ack-g to: ', name)
        axios({
            method: 'post',
            url: backendURL + 'ack-g',
            data: name,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleBye() {
        console.log('Send bye to: ', name)
        axios({
            method: 'post',
            url: backendURL + 'bye',
            data: name,
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    function handleByebye() {
        axios({
            method: 'get',
            url: backendURL + 'byebye',
            headers: {
                'content-type': 'application/json',
                'Authorization' : 'Bearer ' + localStorage.getItem('accessToken')
            }
        });
    }

    return (
        <div className='buttonsClass'>
            <Button name={"login"} onClickFunction={logIn} />
            <Button name={"hello"} onClickFunction={handleHello} />
            <Button name={"ack"} onClickFunction={handleAck} />
            <Button name={"send"} onClickFunction={handleSend} />
            <Button name={"group"} onClickFunction={handleGroup} />
            <Button name={"ack-g"} onClickFunction={handleAckGroup} />
            <Button name={"bye"} onClickFunction={handleBye} />
            <Button name={"byebye"} onClickFunction={handleByebye} />
        </div>
    );
}       
         