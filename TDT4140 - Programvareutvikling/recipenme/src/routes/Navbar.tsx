import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import NavbarLogo from '../components/atoms/NavbarLogo';
import NavbarTitle from '../components/atoms/NavbarTitle';
import NavbarDropdown from '../components/molecules/NavbarDropdown';
import "./NavBar.css"

export default function Navbar(){

    /*
    useEffect(() => {
        //localStorage.setItem('user', '-1')
        const loggedInUser:any = Number(localStorage.getItem('user'));
        if (loggedInUser > -1 && loggedInUser != null) {
            console.log(true);
            const button = document.getElementById('loginNav')!;
            button.innerHTML = "Sign out";
        } else {
            console.log(false);
            const button = document.getElementById('loginNav')!;
            button.innerHTML = "Login";
        }
    }) */

    return(
        <div>
            <nav id="navbarContent">
                <NavbarLogo />
                <NavbarTitle />
                <NavbarDropdown />
            </nav>
            <Outlet/>
        </div>
    )
}