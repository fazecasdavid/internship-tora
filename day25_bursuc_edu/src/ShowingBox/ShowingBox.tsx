import React, { useEffect } from 'react';
import { useState } from 'react';
import Message from '../model/Message'
import "./ShowingBox.css";

export default function ShowingBox() {


    const [utilMessagesState, setUtilMessages] = useState([] as Message[]);
    const [chatMessagesState, setChatMessages] = useState([] as Message[]);
    const [friendsState, setFriends] = useState([] as string[]);
    const wsBackEndUrl = "ws://34.207.124.142:8080";

        useEffect(() => {
            initSocketChatMessages();
            initSocketUtilMessages();
            initSocketFriends();
        }, [])

    function messageToString(message: Message): string{
        return ("[type: "+message.type+", from: "+message.from+", to: "+message.to+" content: "+message.content+" ]");
    }

    function initSocketFriends() {
        const socket = new WebSocket(wsBackEndUrl+"/ws/friends");
        socket.binaryType = "arraybuffer";

        socket.onopen = function () {
            console.log("Connected");
        };

        socket.onmessage = function (event) {
            const friends: string[] = JSON.parse(event.data);
            setFriends(friends);  
        };
    }

    function initSocketUtilMessages() {
        const socket = new WebSocket(wsBackEndUrl+"/ws/message/util");
        socket.binaryType = "arraybuffer";

        socket.onopen = function () {
            console.log("Connected");
        };

        socket.onmessage = function (event) {
            const messages: Message[] = JSON.parse(event.data);
            setUtilMessages(messages);    
        };
    }

    function initSocketChatMessages() {
        const socket = new WebSocket(wsBackEndUrl+"/ws/message/chat");
        socket.binaryType = "arraybuffer";

        socket.onopen = function () {
            console.log("Connected");
        };

        socket.onmessage = function (event) {
            const messages: Message[] = JSON.parse(event.data);
            setChatMessages(messages);    
        };
    }

    return (
        <div className="ShowingBox">
            <div className="friends-box">
                <div>Your friends</div>
                <div id="friends">
                    {friendsState.map(friend => <div>{friend}</div>)}
                </div>
            </div>
            <div className="chat-box">
                <div className="chat-messages">Your chat messages :</div>
                <div id="chat_messages">
                    <ul>            
                    {chatMessagesState.map((message:Message, index: number) => <li key={index}>{messageToString(message)}</li>)}
                    </ul>
                </div>

                <div className="util-messages">Your util messages :</div>
                <div id="util_messages">
                    <ul>            
                    {utilMessagesState.map((message:Message, index: number) => <li key={index}>{messageToString(message)}</li>)}
                    </ul>
                </div>
            </div>
        </div>
    );
}
