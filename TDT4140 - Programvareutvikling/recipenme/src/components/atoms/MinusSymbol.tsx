import React, { MouseEventHandler } from 'react';
import './PlussSymbol.css';
import './MinusSymbol.css';


interface IPlussSymbol {
    onClickFunction: MouseEventHandler
};

const MinusSymbol = (props: IPlussSymbol) => {
    return (
        <div onClick={props.onClickFunction} className="plussContainer">
            <div className="minusHorizontal"></div>
        </div>
    )
};

export default MinusSymbol;
