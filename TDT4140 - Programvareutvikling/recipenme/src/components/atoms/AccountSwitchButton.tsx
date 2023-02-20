import './AccountSwitchButton.css';
import React from 'react';
import { useNavigate } from "react-router-dom";

interface button{
    labelText: string;
    buttonText: string;
    inputPath: string;
}

export default function AccountSwitchButton({labelText, buttonText, inputPath}: button){
    let navigate = useNavigate(); 

    const routeChange = () =>{ 
        let path = inputPath; 
        navigate(path);
      }

    return (
        <div>
            <p className='signUpText'>{labelText}</p>
            <button type='button' className='signUpButton' onClick={routeChange}>{buttonText}</button>
        </div>
    );
}