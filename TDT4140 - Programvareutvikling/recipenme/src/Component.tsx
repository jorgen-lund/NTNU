import React from 'react';

interface Props {
    text: string;
}
// or you can write inline:
// <{ text: string }>, where
// <> Specifies the props that the component takes in
// {} Passes in an object, ans specifies the type

// or you can write (above the function):
// interface Props {
//    text: string
//}
// and then <Props>

// : React.FC
// Typescript Type, tells everyobody that this is a Function Component
// This is a TypeScript type React gives you

export const Component: React.FC<Props> = () => {
    
    return (
        <div>
            <input />
        </div>
    );
};