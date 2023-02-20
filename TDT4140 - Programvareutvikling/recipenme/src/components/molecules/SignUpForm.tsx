import React from 'react';
import AccountButton from '../atoms/AccountButton';
import './LoginForm.css'
import './SignUpForm.css'
import AccountSwitchButton from '../atoms/AccountSwitchButton';
import AccountTextField from '../atoms/AccountTextField';
import { getUserReturn, postUserReturn } from '../../client';
import { useNavigate } from 'react-router-dom';

function getFirstName() {
    let firstName = (document.getElementById("firstNameFieldSignUp") as HTMLInputElement).value;
    return firstName;
}

function getLastName() {
    let lastName = (document.getElementById("lastNameFieldSignUp") as HTMLInputElement).value;
    return lastName;
}

function getUsername() {
    let username = (document.getElementById("usernameFieldSignUp") as HTMLInputElement).value;
    return username;
}

function getEmail() {
    let email = (document.getElementById("emailFieldSignUp") as HTMLInputElement).value;
    return email;
}

function getPassword() {
    let password = (document.getElementById("passwordFieldSignUp") as HTMLInputElement).value;
    return password;
}

function getRepeatedPassword(){
    let repeatedPassword = (document.getElementById("repeatPasswordFieldSignUp") as HTMLInputElement).value;
    return repeatedPassword;
}

function checkPassword() {
    const password1 = getPassword();
    const password2 = getRepeatedPassword();

    if(password1 === password2){
        return true;
    } else {
        return false;
    }
}

function checkIfEmpty(){
    if (getFirstName() === '' || getLastName() === '' || getUsername() === '' || getPassword() === '' || getEmail() === ''){
        return false;
    }
    return true;
}

export default function SignUpForm(){ 
    let navigate = useNavigate(); 

    const handleSignUp = async() => {
        let foo = checkPassword();
        const errorMessage = document.getElementById('errorMessageSignUp')!;
        if(foo == false){
            errorMessage.innerHTML = 'Passwords do not match';
            errorMessage.style.display = 'block';
        } else if(!checkIfEmpty()) {
            errorMessage.innerHTML = 'Please fill out all fields';
            errorMessage.style.display = 'block';
        } else {
            const userID = await postUserReturn(getFirstName(), getLastName(), getUsername(), getEmail(), getPassword());
    
            if(userID === parseInt(userID, 10)){
                errorMessage!.style.display= 'none';
                const user = await getUserReturn(userID);
                const objList = JSON.parse(user);
                const isAdmin = objList.is_superuser;
                console.log('dette får du: ' + user);
                localStorage.setItem('user', userID);
                localStorage.setItem('isAdmin', isAdmin);
                console.log('IS ADMIN: ', isAdmin);
                let path = '/'; 
                navigate(path);
            } else {
                errorMessage!.style.display= 'block';
                console.log('den mener at userID ikke er et tall');
            }
        }
    }
  
    return (
        <div className='signUpDiv'>
            <p className='header'>Sign up</p>
            <form className='signUpForm'>
                <AccountTextField type={'text'} id="firstNameFieldSignUp" placeholder={'First name'}/>
                <AccountTextField type={'text'} id="lastNameFieldSignUp" placeholder={'Last name'}/>
                <AccountTextField type={'text'} id="usernameFieldSignUp" placeholder={'Username'}/>
                <AccountTextField type={'text'} id="emailFieldSignUp" placeholder={'Email'}/>
                <AccountTextField type={'password'} id="passwordFieldSignUp" placeholder={'Password'}/>
                <AccountTextField type={'password'} id="repeatPasswordFieldSignUp" placeholder={'Repeat password'}/>
            </form>
            <p id="errorMessageSignUp" className='signUpError'>Username is already in use</p>
            <div id="button">
                <AccountButton handleClick={() => handleSignUp()} buttonText='Sign up'/>
            </div>
            <br />
            <AccountSwitchButton labelText='Already have an account?' buttonText='Sign in here' inputPath='/login'/>
        </div>
    );
}