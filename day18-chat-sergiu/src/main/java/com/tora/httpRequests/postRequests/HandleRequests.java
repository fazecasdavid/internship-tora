package com.tora.httpRequests.postRequests;

import com.tora.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan("com.tora")
public class HandleRequests {

    @Autowired
    PeerService peerService;

    private String myUser = "mihai";

    //ask for your user
    @PutMapping(value = "/{myUser}")
    public void handleMyUserInput(
            @PathVariable String myUser) {

        this.myUser = myUser;
        peerService.recvMessage(myUser);
        System.out.println("userName set to : " + myUser);
    }


    //REQUEST, toUser
    @PostMapping(value = "/sendFriendRequest/{toUser}")
    public void handleSendFriendRequest(
            @PathVariable String toUser) {

        peerService.handleSendRequest(toUser, myUser);
    }

    //Ack, toUser
    @PostMapping(value = "/sendAckRequest/{toUser}")
    public void handleSendAckRequest(
            @PathVariable String toUser) {

        peerService.handleSendAck(toUser, myUser);
    }


    @PostMapping(value = "/sendGroupInvite/{groupId}{userID}")
    public void handleSendGroupInvite(
            @PathVariable String groupId, @PathVariable String userID) {

        peerService.handleSendGroupRequest(groupId, userID);
    }

    @PostMapping(value = "/sendAckGroupRequest/{groupId}")
    public void handleSendAckGroupRequest(
            @PathVariable String groupId) {

        peerService.handleSendGroupAck(groupId);
    }


    //REQUEST, toUser, content Text your message
    @PostMapping(value = "/sendMessage/{toUser}")
    public void handleSendMessage(
            @PathVariable String toUser,
            @RequestBody String message) {

        peerService.handleSendMessage(myUser, toUser, message);
    }


    @PostMapping(value = "/bye")
    public void handleBye() {
        peerService.handleBye(myUser);
        System.exit(0);
    }

}
