const backendURL = 'http://localhost:8080/'
let username = ''
let chatMessages = []
let utilMessages = []

initSocketFriends();
initSocketUtilMessages();
initSocketChatMessages();

function initSocketFriends() {
    const socket = new WebSocket("ws://localhost:8080/friends");
    socket.binaryType = "arraybuffer";

    socket.onopen = function (event) {
        console.log("Connected");
    };

    socket.onmessage = function (event) {
        $('#friends').innerHTML = "";
        let list = event.data.split('\n');
        let result = "";
        console.log(list);
        for(let element in list){
            result += list[element] + "<br>";
        }
        $('#friends').html(result)
    };
}

function initSocketUtilMessages() {
    const socket = new WebSocket("ws://localhost:8080/message/util");
    socket.binaryType = "arraybuffer";

    socket.onopen = function (event) {
        console.log("Connected");
    };

    socket.onmessage = function (event) {
        $('#util_messages').innerHTML = "";
        let list = event.data.split('\n');
        let result = "";
        console.log(list);
        for(let element in list){
            result += list[element] + "<br>";
        }
        $('#util_messages').html(result)
    };
}

function initSocketChatMessages() {
    const socket = new WebSocket("ws://localhost:8080/message/chat");
    socket.binaryType = "arraybuffer";

    socket.onopen = function (event) {
        console.log("Connected");
    };

    socket.onmessage = function (event) {
        $('#chat_messages').innerHTML = "";
        let list = event.data.split('\n');
        let result = "";
        console.log(list);
        for(let element in list){
            result += list[element] + "<br>";
        }
        $('#chat_messages').html(result);
    };
}


function setUsername() {
    username = $('#username_input').val()
    $('#username_label').html(username)
    console.log('Clicked username: ' + username);
    $.ajax({
            method: 'PUT',
            url: backendURL + 'username',
            data: username,
            contentType: 'text/plain'
        }
    )
}

function handleHello() {
    let name = $('#name_input').val()
    console.log('Send hello to: ', name)
    $.ajax({
            method: 'POST',
            url: backendURL + 'hello',
            data: name,
            contentType: 'text/plain'
        }
    )
}

function handleAck() {
    let name = $('#name_input').val()
    console.log('Send ack to: ', name)
    $.ajax({
            method: 'POST',
            url: backendURL + 'ack',
            data: name,
            contentType: 'text/plain'
        }
    )
}

function handleSend() {
    let userToSend = $('#name_input').val()
    let content = $('#content_input').val()
    let data = JSON.stringify({
        userToSend: userToSend,
        content: content
    })
    console.log('Send message to: ', userToSend)
    $.ajax({
            method: 'POST',
            url: backendURL + 'send',
            data: data,
            contentType: 'application/json'
        }
    )
}

function handleGroup() {
    let groupName = $('#name_input').val()
    let members = $('#content_input').val().split(',')
    let data = JSON.stringify({
        groupName: groupName,
        members: members
    })
    console.log('Send group ' + groupName)
    console.log(members)
    $.ajax({
            method: 'POST',
            url: backendURL + 'group',
            data: data,
            contentType: 'application/json'
        }
    )
}

function handleAckGroup() {
    let name = $('#name_input').val()
    console.log('Send ack-g to: ', name)
    $.ajax({
            method: 'POST',
            url: backendURL + 'ack-g',
            data: name,
            contentType: 'text/plain'
        }
    )
}

function handleBye() {
    let name = $('#name_input').val()
    console.log('Send bye to: ', name)
    $.ajax({
            method: 'POST',
            url: backendURL + 'bye',
            data: name,
            contentType: 'text/plain'
        }
    )
}

function handleByebye() {
    let name = $('#name_input').val()
    console.log('Send bye to: ', name)
    $.ajax({
            method: 'GET',
            url: backendURL + 'byebye',
        }
    )
}

$('#username').on('click', setUsername)
$('#hello').on('click', handleHello)
$('#ack').on('click', handleAck)
$('#send').on('click', handleSend)
$('#group').on('click', handleGroup)
$('#ack-g').on('click', handleAckGroup)
$('#bye').on('click', handleBye)
$('#byebye').on('click', handleByebye)
