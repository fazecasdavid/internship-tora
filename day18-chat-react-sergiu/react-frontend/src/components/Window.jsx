import {v4 as uuid4} from 'uuid';


function Window({configuration}) {
    return (
        <div className="action">
            {
                configuration.windowsElements.map(windowElement =>
                    <div key={uuid4()}>
                        {
                            windowElement.label &&
                            <div>
                                <label htmlFor={windowElement.input.id}>
                                    {windowElement.label}
                                </label><br/>
                            </div>
                        }
                        {
                            windowElement.input &&
                            <div>
                                <input id={windowElement.input.id}
                                       type="text"
                                       placeholder={windowElement.input.text}
                                       onChange={(event) =>
                                           ((windowsElements) => windowElement.input.onChange(event, windowsElements))(configuration.windowsElements)}/>
                            </div>
                        }
                        <br/>
                        {
                            windowElement.button &&
                            <div>
                                <input type="button"
                                       value={windowElement.button.text}
                                       onClick={() => windowElement.button.onClick(configuration.windowsElements)}/><br/>
                            </div>
                        }
                    </div>
                )
            }
        </div>
    )
}

export default Window;
