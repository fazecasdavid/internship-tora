import {ButtonElement, InputElement, WindowConfiguration, WindowElement} from "./domain/windowConfiguration";
import httpService from "./services/httpService";

const INPUT_INDEX_0 = 0;    // login, request, ack, group-ack, group-name, userToSend
const INPUT_INDEX_1 = 1;    // userToInviteToGroup, content

// const loginWindowConfiguration = new WindowConfiguration([new WindowElement('Please login',
//     new InputElement('Type your user', 'myUsername', (event, windowConfiguration) => {
//         windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
//     }),
//     new ButtonElement('Login',
//         (windowConfiguration) => {
//             let myUser = windowConfiguration[INPUT_INDEX_0].input.value;
//             httpService.handleLogin(myUser).catch(console.error);
//         })
//     )]
// );

const sendRequestWindowConfiguration = new WindowConfiguration([new WindowElement('Send request',
    new InputElement('To User', 'sendRequest', (event, windowConfiguration) => {
        windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
    }),
    new ButtonElement('Request',
        (windowConfiguration) => {
            let toUser = windowConfiguration[INPUT_INDEX_0].input.value;
            httpService.handleFriendRequest(toUser).catch(console.error);
        })
    )]
);

const ackRequestWindowConfiguration = new WindowConfiguration([new WindowElement('Ack request',
    new InputElement('To User', 'ackRequest', (event, windowConfiguration) => {
        windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
    }),
    new ButtonElement('Accept',
        (windowConfiguration) => {
            let toUser = windowConfiguration[INPUT_INDEX_0].input.value;
            httpService.handleAckRequest(toUser).catch(console.error);
        })
    )]
);

const acceptGroupInviteWindowConfiguration = new WindowConfiguration([new WindowElement('Accept group invite',
    new InputElement('Group Name', 'groupName', (event, windowConfiguration) => {
        windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
    }),
    new ButtonElement('Accept',
        (windowConfiguration) => {
            let groupName = windowConfiguration[INPUT_INDEX_0].input.value;
            httpService.handleGroupAck(groupName).catch(console.error);
        })
    )]
);

const sendGroupInviteWindowsConfiguration = new WindowConfiguration([new WindowElement('Send group invite',
    new InputElement('Group Name', 'groupId', (event, windowConfiguration) => {
        windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
    }), null),

    new WindowElement(null,
        new InputElement('To User', 'userId', (event, windowConfiguration) => {
            windowConfiguration[INPUT_INDEX_1].input.value = event.target.value
        }),
        new ButtonElement('Request',
            (windowConfiguration) => {
                let groupName = windowConfiguration[INPUT_INDEX_0].input.value;
                let toUser = windowConfiguration[INPUT_INDEX_1].input.value;
                httpService.handleGroupInvite(groupName, toUser).catch(console.log);
            })
    )]
);

const sendMessageWindowConfiguration = new WindowConfiguration([new WindowElement('User',
    new InputElement('To User', 'sendMessageUser', (event, windowConfiguration) => {
        windowConfiguration[INPUT_INDEX_0].input.value = event.target.value
    }), null),
    new WindowElement('Content',
        new InputElement('Content', 'sendMessageContent', (event, windowConfiguration) => {
            windowConfiguration[INPUT_INDEX_1].input.value = event.target.value
        }),
        new ButtonElement('Send',
            (windowConfiguration) => {
                let toUser = windowConfiguration[INPUT_INDEX_0].input.value;
                let content = windowConfiguration[INPUT_INDEX_1].input.value;
                httpService.handleSendMessage(toUser, content).catch(console.error);
            })
    )]
);

export let windowConfigurations = [
    // loginWindowConfiguration,
    sendRequestWindowConfiguration,
    ackRequestWindowConfiguration,
    acceptGroupInviteWindowConfiguration,
    sendGroupInviteWindowsConfiguration,
    sendMessageWindowConfiguration
];
