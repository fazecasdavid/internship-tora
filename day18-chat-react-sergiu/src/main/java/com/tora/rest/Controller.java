package com.tora.rest;

import com.tora.service.PeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private PeerService peerService;
    private String myUser = "sergiu";

    @Autowired
    private Controller(PeerService peerService) {
        this.peerService = peerService;
    }

//    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(value = "/login/{myUser:.+}")
    public void handleMyUserInput(@PathVariable String myUser) {
        this.myUser = myUser;
        peerService.recvMessage(myUser);
        System.out.println("userName set to : " + myUser);
    }

    @PostMapping(value = "/sendFriendRequest/{toUser}")
    public void handleSendFriendRequest(@PathVariable String toUser) {
        peerService.handleSendRequest(toUser, myUser);
    }

    @PostMapping(value = "/sendAckRequest/{toUser}")
    public void handleSendAckRequest(@PathVariable String toUser) {
        peerService.handleSendAck(toUser, myUser);
    }

    @PostMapping(value = "/sendGroupInvite/{groupId}/{userID}")
    public void handleSendGroupInvite(@PathVariable String groupId, @PathVariable String userID) {
        peerService.handleSendGroupRequest(groupId, userID);
    }

    @PostMapping(value = "/sendAckGroupRequest/{groupId}")
    public void handleSendAckGroupRequest(@PathVariable String groupId) {
        peerService.handleSendGroupAck(groupId);
    }

    @PostMapping(value = "/sendMessage/{toUser}")
    public void handleSendMessage(@PathVariable String toUser, @RequestBody String message) {
        peerService.handleSendMessage(myUser, toUser, message);
    }

    @PostMapping(value = "/bye")
    public void handleBye() {
        peerService.handleBye(myUser);
        System.exit(0);
    }

    @GetMapping(value = "/utilMessages")
    public List<String> retrieveUtilMessages() {
        System.out.println("served utilMessages");
        return peerService.retrieveNewUtilMessages();
    }

    @GetMapping(value = "/chatMessages")
    public List<String> retrieveChatMessages() {
        System.out.println("served chatMessages");
        return peerService.retrieveNewChatMessages();
    }

}
