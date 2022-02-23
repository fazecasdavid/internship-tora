import React from 'react';
import './Button.css';

export type ButtonProps = {
    name: string
    onClickFunction : any
}

export default function Button({ name, onClickFunction }: ButtonProps) {
    return (
        <div className="Button">
            <button id={name} type="button" onClick={onClickFunction}>{name}</button>
        </div>
    );
}