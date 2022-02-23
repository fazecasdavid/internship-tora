import {useEffect, useState} from "react";
import WsService from "../services/wsService";

const wsService = new WsService();

function ChatBox() {

    const [utilMessages, setUtilMessages] = useState('');
    const [chatMessages, setChatMessages] = useState('');

    useEffect(() => {
        wsService.startListeners(setUtilMessages, setChatMessages)
    }, []);

    return (
        <div className='chat-box'>
            <label>Util Messages</label>
            <div id="util_messages" className="action" dangerouslySetInnerHTML={{__html: utilMessages}}/>

            <label>Chat Messages</label>
            <div id="chat_messages" className="action" dangerouslySetInnerHTML={{__html: chatMessages}}/>
        </div>
    )
}

export default ChatBox
