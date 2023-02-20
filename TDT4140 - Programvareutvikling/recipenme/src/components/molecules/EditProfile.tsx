import AccountTextField from '../atoms/AccountTextField';
import "./EditProfile.css"
import AccountButton from '../atoms/AccountButton';
import {getUserReturn, UpdateProfile } from '../../client';
import React from 'react';
import { useNavigate } from 'react-router-dom';
const axios = require('axios').default;
axios.defaults.baseURL = 'http://127.0.0.1:8000/app1';

function getFirstName() {
  let firstName = (document.getElementById("firstNameFieldSignUp") as HTMLInputElement).value;
  console.log(firstName);

  return firstName;
}

function getLastName() {
  let lastName = (document.getElementById("lastNameFieldSignUp") as HTMLInputElement).value;
  console.log(lastName);
  return lastName;
}

function getUsername() {
  let username = (document.getElementById("usernameFieldSignUp") as HTMLInputElement).value;
  console.log(username);

  return username;
}

function getEmail() {
  let email = (document.getElementById("emailFieldSignUp") as HTMLInputElement).value;
  console.log(email);

  return email;
}

function getPassword() {
  let password = (document.getElementById("passwordFieldSignUp") as HTMLInputElement).value;
  console.log(password);

  return password;
}

function getRepeatedPassword(){
  let repeatedPassword = (document.getElementById("repeatPasswordFieldSignUp") as HTMLInputElement).value;
  console.log(repeatedPassword);
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

export default function EditProfile() {

let navigate = useNavigate();

//==========user
const userID = Number(localStorage.getItem('user'));

const routeChange = async () =>{

if(checkIfEmpty()){
  if(checkPassword()){
    await UpdateProfile(userID, getFirstName(), getLastName(), getUsername(), getEmail(), getPassword());

    let path = '/profile'; 
    navigate(path);
  }
}
else {
  console.log("")
  }
}
function createElements(dict: any){
 
  return(
    <form className="EPTextFieldBox">
      <p className='FirstName'> First Name</p> 
      <AccountTextField type={'text'} id="firstNameFieldSignUp" placeholder={dict.first_name} />
      <p className='LastName'>Last Name</p>
      <AccountTextField type={'text'} id="lastNameFieldSignUp" placeholder={dict.last_name} />
      <p className='Username'>Username</p>
      <AccountTextField type={'text'} id="usernameFieldSignUp" placeholder={dict.username}/>
      <p className='E-mail'>E-mail</p>
      <AccountTextField type={'text'} id="emailFieldSignUp" placeholder={dict.email} />
      <p className='Password'>Password</p>
      <AccountTextField type={'password'} id="passwordFieldSignUp" placeholder={' password'} />
      <p className='RepeatPassword'>Repeat Password</p>
      <AccountTextField type={'password'} id="repeatPasswordFieldSignUp" placeholder={'Repeat password'}/>
    </form>)
}

const [data, setData] = React.useState<JSX.Element>();
React.useEffect( () => {
  axios.get('/user/' + localStorage.getItem('user')).then((resolve: any) => {
    let rawData = resolve.data;
    setData(createElements(rawData));
    console.log(rawData);
  })
}, []);

  return (
    <div className='EPOuterProfileBox'>
        <h2 className='EPEditProfileHeader'>Account Information</h2>
        <div className='EPContentBox'>
                {data}
            <div className='EPBottomBox'>
                <AccountButton handleClick={routeChange} buttonText='Save Changes'/>
            </div>
        </div>
        <br />
    </div>
  );
  }