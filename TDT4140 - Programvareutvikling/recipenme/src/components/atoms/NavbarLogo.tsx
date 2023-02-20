import React from "react";
import appLogo from "../../images/recipenmeLogo.png";
import "./NavbarLogo.css";


export default function NavbarLogo() {
    return (
        <div>   
            <img id="image" 
                src={appLogo}
                alt="recipenme logo"
            />
        </div>
    )
}