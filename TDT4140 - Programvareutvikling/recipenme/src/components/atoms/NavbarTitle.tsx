import React from "react";
import "./NavbarTitle.css";
import { useNavigate } from "react-router-dom";

export default function NavbarTitle() {
    let navigate = useNavigate(); 

    const routeChange = () =>{ 
        let path = '/'; 
        navigate(path);
      }

    return (
        <button id='navbarTitle' onClick={routeChange}>RecipeN'Me</button>
    )
}
