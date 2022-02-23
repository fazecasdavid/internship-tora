import React from 'react';
import Buttons from './Buttons';
import "./Menu.css";
import { setContent, setName, setPassword, setUserName } from '../actions/index';
import { useDispatch } from 'react-redux';

export default function Menu() {
    const dispatch = useDispatch();
    const handleSetUsername = (event: React.ChangeEvent<HTMLInputElement>) => {
        let username = event.target.value;
        dispatch(setUserName(username));
      }

      const handleSetName = (event: React.ChangeEvent<HTMLInputElement>) => {
        let name = event.target.value;
        dispatch(setName(name));
      }

      const handleSetContent = (event: React.ChangeEvent<HTMLInputElement>) => {
        let content = event.target.value;
        dispatch(setContent(content));
      }

      const handleSetPassword = (event: React.ChangeEvent<HTMLInputElement>) => {
        let content = event.target.value;
        dispatch(setPassword(content));
      }

    return (
        <div className="Menu">
            <div className="InputFields">

                <label htmlFor="username_input">USERNAME:</label>
                <input type="text" id="username_input" onChange = {handleSetUsername} name="username_input" className="Input" /><br /><br />
                
                <label htmlFor="password_input">PASSWORD:</label>
                <input type="text" id="password_input" onChange = {handleSetPassword} name="password_input" className="Input" /><br /><br />

                <label htmlFor="name_input">NAME:</label>
                <input type="text" id="name_input" onChange = {handleSetName} name="name_input" className="Input" /><br /><br />

                <label htmlFor="content_input">CONTENT:</label>
                <input type="text" id="content_input" onChange = {handleSetContent} name="content_input" className="Input" /><br /><br />
            </div>
                <Buttons />
        </div>
    );
}


