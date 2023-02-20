import * as React from 'react';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import ListSubheader from '@mui/material/ListSubheader';
import IconButton from '@mui/material/IconButton';
import InfoIcon from '@mui/icons-material/Info';
import { useNavigate } from "react-router-dom";
import { getRecipe, getRecipeReturnAsync, getUserReturn } from '../../client';
import RecipePage, { insertOwner, insertPhoto, insertID, insertTitle, insertCategoryAndTimeEstimate, insertIngredients, insertPreparation, recipePageVisibility } from './RecipePage';

async function getRecipeFromId(idNumber: number) {
  recipePageVisibility();
  var id = '' + idNumber;

  const recipe = await getRecipeReturnAsync(id);
  console.log(3);

  const objList = JSON.parse(recipe);
  console.log(4);

  let title = objList.title;
  let category = objList.category;
  let timeEstimate = objList.time_estimate;
  let image = objList.image;
  console.log(image);
  let ingredients = objList.ingredients;
  let preparation = objList.preparation;
  let owner = Number(objList.owner_id);
  console.log('owner: ' + owner);
  const fullOwner = await getUserReturn(owner);
  const objList2 = JSON.parse(fullOwner);
  console.log(objList2);
  const fName = objList2.first_name;
  console.log('f: ' + fName);

  const lName = objList2.last_name;
  const string = fName + ' ' + lName;


  console.log(category);
  insertTitle(title);
  insertOwner(string);
  insertPhoto(image);
  insertCategoryAndTimeEstimate(category, timeEstimate);
  insertIngredients(ingredients);
  insertPreparation(preparation);
  console.log(2);
  insertID(idNumber);
  console.log(4);
}

export default function TitlebarImageList(itemdata: Array<any>) {
  //let navigate = useNavigate();

  /*function routeChange(id: number) {
    let path = `/recipe/`;
    let navigate = useNavigate();
    getRecipeFromId(id);
  }*/

  return (
    <div>
      <RecipePage></RecipePage>
      <ImageList cols = {3} sx={{ width: '70%' , height: '100%', margin: 'auto', marginTop: '3vw', padding: '0px', }} >
        {itemdata.map((item) => (
          <ImageListItem key={item.img} onClick={() => {getRecipeFromId(item.recipeid)}}>
            <img
              src={`${item.img}?w=164&h=164&fit=crop&auto=format`}
              srcSet={`${item.img}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
              alt={item.title}
              loading="lazy"
            />
            <ImageListItemBar
              sx={{ color: '#76af67' }}
              title={item.title}
              subtitle={item.author}
              actionIcon={
                <IconButton
                  sx={{ color: '#76af67' }}
                  aria-label={`info about ${item.title}`}
                >
                  <InfoIcon />
                </IconButton>
              }
            />
          </ImageListItem>
        ))}      
      </ImageList>
    </div>
    
  );
}

