import React from 'react';
import './FormButton.css';

interface IFormButton {
    label: string,
    id: string,
    handleClick: () => void;
};

const FormButton = (props: IFormButton) => {
    return (
        <div>
            <button id={props.id} onClick={props.handleClick} className="formButton" type="button">{props.label}</button>
        </div>
    )
};

export default FormButton;
