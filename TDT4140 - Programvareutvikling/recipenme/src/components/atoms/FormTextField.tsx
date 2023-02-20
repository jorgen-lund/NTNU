import React from 'react';
import './FormTextField.css';

interface IFormTextField {
    placeholder: string,
    id: string
};

const FormTextField = (props: IFormTextField) => {
    return (
        <div className="formTextFieldContainer">
            <input className="formTextField" id = {props.id} placeholder={props.placeholder} type="text" />
        </div>
    )
};

export default FormTextField;
