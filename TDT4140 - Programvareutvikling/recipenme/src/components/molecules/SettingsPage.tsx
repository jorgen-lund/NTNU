import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SettingsOption from "../atoms/SettingsOption";
import DeletePopup from "./DeletePopup";
import './SettingsPage.css';

export default function SettingsPage() {
    let navigate = useNavigate();

    /* Not working */
    const editOption = () => {
        let path = '/edit-profile';
        navigate(path);
    }

    return (
    <div className='settingsWrapper'>
        <h1>Settings</h1>
        <SettingsOption handleClick={editOption} text='Edit account' />
        <br />
        <DeletePopup id='popup'/>
    </div>
    );
}
