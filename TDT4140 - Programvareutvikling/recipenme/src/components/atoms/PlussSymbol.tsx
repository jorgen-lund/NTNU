import React, { MouseEventHandler } from 'react';
import './PlussSymbol.css';

interface IPlussSymbol {
    onClickFunction: MouseEventHandler
};

const PlussSymbol = (props: IPlussSymbol) => {
    return (
        <div onClick={props.onClickFunction} className="plussContainer">
            <div className="vertical"></div>
            <div className="horizontal"></div>
        </div>
    )
};

export default PlussSymbol;
