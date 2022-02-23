export class WindowConfiguration {
    public windowsElements: WindowElement[];

    constructor(windowsElements: WindowElement[]) {
        this.windowsElements = windowsElements;
    }
}

export class WindowElement {
    public label: string;
    public input: InputElement;
    public button: ButtonElement;

    constructor(label: string, input: InputElement, button: ButtonElement) {
        this.label = label;
        this.input = input;
        this.button = button;
    }
}

export class InputElement {
    public value = '';
    public text: string;
    public onChange: any;
    public id: string;

    constructor(text: string, id: string, onChange: any) {
        this.text = text;
        this.id = id;
        this.onChange = onChange;
    }
}

export class ButtonElement {
    public text: string;
    public onClick: any;

    constructor(text: string, onClick: any) {
        this.text = text;
        this.onClick = onClick;
    }
}
