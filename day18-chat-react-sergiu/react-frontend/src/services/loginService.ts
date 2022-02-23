import axios from 'axios';

class LoginService {
    private baseURL = 'https://auth-dev-1.app.toradev.net/auth/realms/INTERNSHIP/protocol/openid-connect/token';

    public async getAndSetToken(username: string, password: string) {
        console.log(username, password);
        const params = new URLSearchParams()
        params.append('grant_type', 'password')
        params.append('client_id', 'chat-client')
        params.append('username', username)
        params.append('password', password)

        const response = await axios.post<any>(
            this.baseURL,
            params,
            {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
        )
        console.log(response.data.access_token)
        localStorage.setItem('token', `Bearer ${response.data.access_token}`)
    }

}

export default new LoginService();
