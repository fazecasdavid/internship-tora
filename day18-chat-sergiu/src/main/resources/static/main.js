function sendRequest(){
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("sendRequest").value;

    xhttp.open("POST", "sendFriendRequest/" + toUser, true);
    xhttp.send();
}

function ackRequest(){
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("ackRequest").value;

    xhttp.open("POST", "sendAckRequest/" + toUser, true);
    xhttp.send();
}

function bye(){
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", "bye/", true);
    xhttp.send();
}

function sendMessage(){
    var xhttp = new XMLHttpRequest();

    let toUser = document.getElementById("sendMessageUser").value;
    let content = document.getElementById("sendMessageContent").value;

    xhttp.open("POST", "sendMessage/" + toUser, true);
    xhttp.send(content);
}