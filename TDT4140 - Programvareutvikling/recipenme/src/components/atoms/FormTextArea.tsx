import React from 'react';
import './FormTextArea.css';

interface IFormTextArea {
    placeholder: string,
    id: string
};

const FormTextArea = (props: IFormTextArea) => {
    return (
        <div className="formTextAreaContainer">
            <textarea id={props.id} className="formTextArea" placeholder={props.placeholder}></textarea>
        </div>
    )
};

export default FormTextArea;
