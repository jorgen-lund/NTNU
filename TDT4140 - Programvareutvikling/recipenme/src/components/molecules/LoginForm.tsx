import React, { useState } from 'react';
import AccountButton from '../atoms/AccountButton';
import './LoginForm.css'
import AccountTextField from '../atoms/AccountTextField';
import AccountSwitchButton from '../atoms/AccountSwitchButton';
import { getUser, getUserReturn, loginReturn } from '../../client';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

function getUserName() {
    let username = (document.getElementById("usernameFieldLogin") as HTMLInputElement).value;
    return username;
}
function getPassword() {
    let password = (document.getElementById("passwordFieldLogin") as HTMLInputElement).value;
    return password;
}

export default function LoginForm(){
    let navigate = useNavigate(); 
    let userFirstName;
    let userLastName;
    let userEmail;
    let userUsername;
    let isAdmin;

    const routeChange = async () =>{ 
        const userID = await loginReturn(getUserName(), getPassword());
        console.log("RESPONSE: " + userID);
        const errorMessage = document.getElementById('errorMessage');
    
        if(userID === parseInt(userID, 10)){
            errorMessage!.style.display= 'none';

            const user = await getUserReturn(userID);
            console.log('dette får du: ' + user);
            const objList = JSON.parse(user);
            userFirstName = objList.first_name;
            userLastName = objList.last_name;
            userEmail = objList.email;
            userUsername = objList.username;
            isAdmin = objList.is_superuser;
            console.log('IS ADMIN: ', isAdmin);

            localStorage.setItem('user', userID);
            localStorage.setItem('isAdmin', isAdmin);
            
            let path = '/'; 
            navigate(path);
        } else {
            errorMessage!.style.display= 'block';
        }
      }

    return (
        <div className='loginForm'>
            <p className='header'>Login</p>
            <form className='form'>
                <AccountTextField type={'text'} id={'usernameFieldLogin'} placeholder={'Username'}/>
                <AccountTextField type={'password'} id={'passwordFieldLogin'} placeholder={'Password'}/>
            </form>
            <p id="errorMessage" className='loginError'>Username or password is incorrect</p>
            <div id="button">
                <AccountButton handleClick={routeChange} buttonText='Login'/>
            </div>
            <br />
            <AccountSwitchButton labelText="Don't have an account?" buttonText='Sign up here' inputPath='/signup'/>
        </div>
    );
}


