import './App.css';
import Window from './components/Window';
import ChatBox from './components/ChatBox';
import {v4 as uuid4} from 'uuid';
import {windowConfigurations} from "./windowConfigurations";
import LoginWindow from "./components/LoginWindow";


function App() {
    return (
        <div className='mainContainer'>
            <LoginWindow/>
            {
                windowConfigurations.map(windowConfiguration =>
                    <Window key={uuid4()} configuration={windowConfiguration}/>)
            }
            <ChatBox/>
        </div>
    );
}

export default App;
