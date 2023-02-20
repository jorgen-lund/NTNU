import './Popup.css';

interface props {
    id: string;
    handleClick: () => void;
    content: any;
}

export default function Popup ({id, handleClick, content}:props){
    return (
        <div id={id} className='deletePopupOuter'>
            <div className="deletePopupInner">
                <span className="closePopup" onClick={handleClick}>x</span>
                {content}
            </div>
        </div>
    );
}