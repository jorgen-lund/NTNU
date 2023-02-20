import React, { ChangeEventHandler, useState } from 'react';
import './NewRecipeForm.css';
import FormButton from '../atoms/FormButton';
import FormTextField from '../atoms/FormTextField';
import FormTextArea from '../atoms/FormTextArea';
import MinusSymbol from '../atoms/MinusSymbol';
import '../atoms/FormTextArea.css';
import PlussSymbol from '../atoms/PlussSymbol';
import { Button } from 'react-bootstrap';
import { postRecipe } from '../../client';
import './NewRecipeForm.css';
import '../atoms/FormTextField';
import '../atoms/FormTextArea.css';
import { useNavigate } from 'react-router-dom';


function NewIngredientField() {
    const minus = document.getElementById('minusVisibility');
    minus!.style.display= "block";
    const div = document.createElement('div');
    div.className = 'formTextFieldContainer';
    const input = document.createElement("INPUT");
    input.setAttribute("type", "text");
    input.setAttribute("placeholder", "Ingredient and amount");
    input.className = 'formTextField';
    div.appendChild(input);
    var newList = document.getElementById('list')?.appendChild(div);
    return newList;
};

function RemoveIngredientField() {
    var list = document.getElementById('list');
    var newList = list?.removeChild(list.lastChild!);
    if (list?.childElementCount! < 2) {
        const minus = document.getElementById('minusVisibility');
        minus!.style.display= "none";
    }
};

function IngredientField() {
    var list = document.getElementById('list');
    if (list?.childElementCount! < 2) {
        const minus = document.getElementById('minusVisibility');
        minus!.style.display= "none";
    }
    return <FormTextField id={'ingredientsAndAmount'} placeholder={"Ingredient and amount"}></FormTextField>;
};

function getTitle() {
    let firstName = (document.getElementById("title") as HTMLInputElement).value;
    return firstName;
}

function getTimeEstimate() {
    let timeEstimate = (document.getElementById("timeEstimate") as HTMLInputElement).value;
    return timeEstimate;
}

function getImage() {
    let fil = (document.getElementById("file") as HTMLInputElement).files.item(0);
    return fil
}

function getIngredients() {
    const nodeList = document.getElementById("list")?.childNodes;    
    let numb;
    let ingredients = "";
    if (nodeList) {
        numb = nodeList.length;
        if (numb) {            
            for (let child = 0; child < numb; child ++)  {
                let ingredient = (nodeList[child].childNodes[0] as HTMLInputElement).value;                
                if(ingredients.length > 0) {
                    ingredients = ingredients.concat(",");

                }
                ingredients = ingredients.concat(ingredient);
            }
        }
    }
    return ingredients;
}

function getCategories() {
    let checkedCategories = "";
    for (let i = 0; i < 5; i++) {        
        let checkbox = document.getElementsByClassName('checkbox')[i] as HTMLInputElement;
        if (checkbox.checked == true) {
            
            if(checkedCategories.length > 0) {
                checkedCategories = checkedCategories.concat(",");
            }
            let checkboxValue = checkbox.value;
            checkedCategories = checkedCategories.concat(checkboxValue)            
        }
    }
    return checkedCategories;
}

function getPreparation() {
    let preparation = (document.getElementById("preparations") as HTMLInputElement).value;
    return preparation;
}

const NewRecipeForm = () => {
    let navigate = useNavigate(); 

    const routeChange = () =>{ 
        let path = '/profile'; 
        navigate(path);
        window.location.reload();
      }
      
    return (
        <div className="newRecipeContainer">
            <h2>Fill in the fields for your new recipe!</h2>
            <FormTextField id={"title"} placeholder={"Title"}></FormTextField>
            <FormTextField id={"timeEstimate"} placeholder={"Time estimate"}></FormTextField>
            <div className="fileContainer">
                <label className="fileLabel" htmlFor="file">Choose an image
                    <input className="fileInput" id="file" name = 'picture' type="File" accept = 'image/png, image/jpeg' border-style="none"/>
                </label> <br />
            </div>
            <h3 className="ingredientsLabel">Ingredients:</h3>
            <div className="ingredientOuterContainer">
                <div className="ingredientContainer" id="count">
                    <div id="list" className="ingredientList">{IngredientField()}</div>
                </div>
                <div className="pluss">
                    <PlussSymbol onClickFunction={NewIngredientField}></PlussSymbol>
                </div>
                <div id="minusVisibility" className="minus">
                    <MinusSymbol onClickFunction={RemoveIngredientField}></MinusSymbol>
                </div>
            </div>
            <div className="categories" id="categoryContainer">
                <h3 className="categoryLabel">Choose categories:</h3>
                <input className="checkbox" id="breakfast" value="Breakfast" type="checkbox"/>
                <label className="checkLabel" htmlFor="breakfast">Breakfast</label>
                <input className="checkbox" id="simpleDish" value="Simple dish" type="checkbox" />
                <label className="checkLabel" htmlFor="simpleDish">Simple dish</label>
                <input className="checkbox" id="vegan" value="Vegan" type="checkbox" />
                <label className="checkLabel" htmlFor="vegan">Vegan</label>
                <input className="checkbox" id="italian" value="Italian" type="checkbox" />
                <label className="checkLabel" htmlFor="italian">Italian</label>
                <input className="checkbox" id="glutenfree" value="Gluten-free" type="checkbox" />
                <label className="checkLabel" htmlFor="glutenfree">Gluten-free</label>
            </div>
            <FormTextArea id={"preparations"} placeholder={"Preparations"}></FormTextArea>
            <div className="buttonContainer">
                <FormButton label={'Submit'} id={'submitRecipe'} handleClick={() => (postRecipe(localStorage.getItem('user'),getTitle(),getImage(), getTimeEstimate(),getCategories(),getPreparation(),getIngredients()), routeChange())}></FormButton>
            <div/>
        </div>
        </div>
    )
};
export default NewRecipeForm;