import React from "react"
import "./tempStyles.css"

import biff from "./tempImages/biff.jpg"
import laks from "./tempImages/laks.jpg"
import pizza from "./tempImages/pizza.jpg"
import spagh_bolo from "./tempImages/spagh_bolo.jpg"
import taco from './tempImages/taco.jpg'
import tomatsuppe from './tempImages/tomatsuppe.jpg'
// import taco from "../images/taco.jpg"
// import tomat_suppe from "../images/tomat_suppe.jpg"

const MainBody = () => {

    return (
        <div className="MainBody">
            <ul className="MainBody--list">
                <li className="MainBody--biff"> 
                    <img 
                        src={biff}
                        alt="biff"
                        width = "400"
                        height = "200"
                    />
                    <h4>Biff</h4>
                </li>
                <li className="MainBody--laks">
                    <img 
                        src={laks}
                        alt="laks"
                        width = "400"
                        height = "200"
                    />
                    <h4>Laks</h4>
                </li>
                <li className="MainBody--pizza">
                    <img
                        src={pizza}
                        alt="pizza"
                        width = "400"
                        height = "200"
                    />
                    <h4>Pizza</h4>
                </li>
                <li className="MainBody--spagh_bolo">
                    <img
                        src={spagh_bolo}
                        alt="pizza"
                        width = "400"
                        height = "200"
                    />
                    <h4>Spaghetti Bolognese</h4>
                </li>
                <li>
                    <img
                        src={taco}
                        alt="pizza"
                        width = "400"
                        height = "200"
                    />
                    <h4>Taco</h4>
                </li>
                <li>
                    <img
                        src={tomatsuppe}
                        alt="pizza"
                        width = "400"
                        height = "200"
                    />
                    <h4>Tomatsuppe</h4>    
                </li>
            </ul>
        </div>
    )
}

export default MainBody