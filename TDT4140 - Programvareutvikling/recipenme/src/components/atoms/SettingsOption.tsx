import './SettingsOption.css';

interface optionText {
    text: string;
    handleClick: () => void;
}

export default function SettingsOption ({text, handleClick}: optionText){
    return <button onClick={handleClick} className = 'settingsOption'>{text}</button>
}
