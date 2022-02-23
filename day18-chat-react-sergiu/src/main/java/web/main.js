const baseURL = "http://localhost:8080/";


function login() {
    var xhttp = new XMLHttpRequest();
    let myUser = document.getElementById("myUsername").value;

    xhttp.open("POST", baseURL + "login/" + myUser, true);
    xhttp.send();
}

function sendRequest() {
    console.log("sendRequest");
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("sendRequest").value;

    xhttp.open("POST", baseURL + "sendFriendRequest/" + toUser, true);
    xhttp.send();
}

function ackRequest() {
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("ackRequest").value;

    xhttp.open("POST", baseURL + "sendAckRequest/" + toUser, true);
    xhttp.send();
}

function sendGroupInvite() {
    var xhttp = new XMLHttpRequest();

    let groupId = document.getElementById("groupId").value;
    let toUser = document.getElementById("userId").value;

    xhttp.open("POST", baseURL + "sendGroupInvite/" + groupId + "/" + toUser, true);
    xhttp.send();
}

function ackGroupInvite() {
    var xhttp = new XMLHttpRequest();

    let ackGroupId = document.getElementById("ackGroupId").value;

    xhttp.open("POST", baseURL + "sendAckGroupRequest/" + ackGroupId, true);
    xhttp.send();
}

function bye() {
    console.log("bye")
    getUtilMessages();
//    var xhttp = new XMLHttpRequest();
//    xhttp.open("POST", baseURL+"bye/", true);
//    xhttp.send();
}

function sendMessage() {
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("sendMessageUser").value;
    let content = document.getElementById("sendMessageContent").value;


    xhttp.open("POST", baseURL + "sendMessage/" + toUser, true);
    xhttp.setRequestHeader('Content-type', 'text/plain');
    xhttp.send(content);
}

function getUtilMessages() {
    var xhttp = new XMLHttpRequest();

    xhttp.open("GET", baseURL + "utilMessages", true);
    xhttp.responseType = 'text';
    xhttp.onload = appendUtilMessages(xhttp.response)
    xhttp.send(null);
}

function appendUtilMessages(response) {
    let utilMessagesInput = document.getElementById("utilMessages");
    let currentContent = utilMessagesInput.value;
    utilMessagesInput.setAttribute("value", currentContent + response)
}


function getChatMessages() {
    var xhttp = new XMLHttpRequest();

    while (true) {
        xhttp.open("GET", baseURL + "chatMessages", true);
        xhttp.send();
        xhttp.onload = appendChatMessages(xhttp.response);
    }
}

function appendChatMessages(response) {
    let utilMessagesInput = document.getElementById("chatMessages");
    let currentContent = utilMessagesInput.value;
    utilMessagesInput.setAttribute("value", currentContent + response)
}


let receivedUtilMessages = ''
let receivedChatMessages = ''
const webSocketEndpoint = 'ws://localhost:8080/'

function initSocketUtilMessages() {
    const socket = new WebSocket(webSocketEndpoint + 'messages/util');
    socket.onopen = function (event) {
        console.log("Connected");
    };

    socket.onmessage = function (event) {
        const list = event.data.split('\n');
        console.log(list);
        for (const element of list) {
            receivedUtilMessages += element + '<br>';
        }
        $('#util_messages').html(receivedUtilMessages)
    };
}

function initSocketChatMessages() {
    const socket = new WebSocket(webSocketEndpoint + 'messages/chat');
    socket.onopen = function (event) {
        console.log("Connected");
    };

    socket.onmessage = function (event) {
        const list = event.data.split('\n');
        console.log(list);
        for (const element of list) {
            receivedChatMessages += element + '<br>';
        }
        $('#chat_messages').html(receivedChatMessages)
    };
}

initSocketUtilMessages();
initSocketChatMessages();



