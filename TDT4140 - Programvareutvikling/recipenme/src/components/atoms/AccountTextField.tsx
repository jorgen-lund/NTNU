import React from 'react';
import './AccountTextField.css'

interface inputPlaceholder {
    placeholder: string;
    id: string;
    type: string;
}

export default function AccountTextField({placeholder, id, type}: inputPlaceholder){
    return (
        <div>
            <input type={type} id={id} placeholder={placeholder} className='textfield' />
            <br />
        </div>
    );
}