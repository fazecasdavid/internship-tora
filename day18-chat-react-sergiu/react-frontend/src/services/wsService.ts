class WsService {
    private webSocketEndpoint = 'ws://localhost:8080'

    public receivedUtilMessages = ''
    public receivedChatMessages = ''

    private setUtilMessages: any;
    private setChatMessages: any;

    public startListeners(setUtilMessages: any, setChatMessages: any) {
        this.initSocketMessagesListeners(this.webSocketEndpoint + '/messages/util', false)
        this.initSocketMessagesListeners(this.webSocketEndpoint + '/messages/chat', true)

        this.setChatMessages = setChatMessages
        this.setUtilMessages = setUtilMessages
    }

    private initSocketMessagesListeners(endpoint: string, chat: boolean) {
        const socket = new WebSocket(endpoint);
        socket.onopen = () => {
            console.log("Connected");
        };

        socket.onmessage = (messageEvent) => {
            const list = messageEvent.data.split('\n');
            console.log(list);
            for (const element of list) {
                if (chat) {
                    this.receivedChatMessages += element + '<br>';
                } else {
                    this.receivedUtilMessages += element + '<br>';
                }
            }
            this.setChatMessages(this.receivedChatMessages)
            this.setUtilMessages(this.receivedUtilMessages)
        };
    }
}

export default WsService;
