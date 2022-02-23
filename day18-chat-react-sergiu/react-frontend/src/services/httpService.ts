import axios, {AxiosInstance} from 'axios';

class HttpService {

    private baseURL = 'http://localhost:8080';
    private config: any;
    private api: AxiosInstance;

    constructor() {
        this.config = {
            headers: {
                'Content-Type': 'application/json',
                Authorization: null
            }
        }
        this.api = axios.create(this.config)

    }

    public handleLogin(myUser: string) {

        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/login/${myUser}`, {}, {headers: {
            'content-type': 'application/json',
                'Authorization' : localStorage.getItem('token')
        }});
    }

    private static getAuthorizationBearer() {
        return {headers: {'Authorization': localStorage.getItem('token')}}
    }

    public handleFriendRequest(userToSend: string) {
        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/sendFriendRequest/${userToSend}`, {}, HttpService.getAuthorizationBearer());
    }

    public handleAckRequest(userToSend: string) {
        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/sendAckRequest/${userToSend}`, {},  HttpService.getAuthorizationBearer());
    }

    public handleGroupAck(groupName: string) {
        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/sendAckGroupRequest/${groupName}`, {}, HttpService.getAuthorizationBearer());
    }

    public handleGroupInvite(groupName: string, userToSend: string) {
        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/sendGroupInvite/${groupName}/${userToSend}`, {}, HttpService.getAuthorizationBearer());
    }

    public handleSendMessage(userToSend: string, content: string) {
        if (localStorage.getItem('token'))
            return axios.post<any>(`${this.baseURL}/sendMessage/${userToSend}`, content,
                {headers: {'Content-Type': 'text/plain', ...(HttpService.getAuthorizationBearer().headers)}});
    }
}

export default new HttpService();
