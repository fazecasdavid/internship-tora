package com.tora.controller;

import com.tora.dto.GroupDto;
import com.tora.dto.SendDto;
import com.tora.model.Message;
import com.tora.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ChatController {
    private PeerService peerService = null;
    private String username = "default";

    @Autowired
    public ChatController(PeerService peerService) {
        this.peerService = peerService;
    }

    @PutMapping("/username")
    public ResponseEntity<String> setUsername(@RequestBody String username) {
        this.username = username;
        System.out.println(username);
        peerService.startReceivers(username);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Response with header using ResponseEntity");
    }

    @GetMapping("/byebye")
    public ResponseEntity<?> handleByebye() {
        Message message = Message.builder()
                .from(username)
                .type(Message.Type.BYE).build();

        peerService.handleByebye(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<?> handleRequest(@RequestBody String userToSend) {
        Message message = Message.builder()
            .from(username)
            .to(userToSend)
            .type(Message.Type.REQUEST).build();

        peerService.sendMessageUtil(message);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Response with header using ResponseEntity");
    }

    @PostMapping("/ack")
    public ResponseEntity<?> handleAck(@RequestBody String userToAck) {
        System.out.println(userToAck);
        Message message = Message.builder()
            .from(username)
            .to(userToAck)
            .type(Message.Type.ACK).build();

        peerService.sendMessageUtil(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<?> handleSend(@RequestBody SendDto sendDto) {
        System.out.println(sendDto.getUserToSend());
        Message message = Message.builder()
            .from(username)
            .to(sendDto.getUserToSend())
            .content(sendDto.getContent())
            .type(Message.Type.MESSAGE).build();

        peerService.sendMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/bye")
    public ResponseEntity<?> handleBye(@RequestBody String userToSend) {
        Message message = Message.builder()
            .from(username)
            .to(userToSend)
            .type(Message.Type.BYE).build();

        peerService.sendMessageUtil(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<?> handleGroup(@RequestBody GroupDto groupDto) {
        System.out.println(groupDto);
        peerService.createGroup(groupDto.getGroupName(), groupDto.getMembers());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/ack-g")
    public ResponseEntity<?> handleGroupAck(@RequestBody String groupToAck) {
        Message message = Message.builder()
            .from(username)
            .to(groupToAck)
            .type(Message.Type.GROUP_ACK).build();

        peerService.sendMessageUtil(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
