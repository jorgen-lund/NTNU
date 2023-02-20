const axios = require('axios').default;
axios.defaults.baseURL = 'http://127.0.0.1:8000/app1';
let userID: any;
let userData: any;
let recipeData: any;
let deleteResponse: any;
let userRecipedata: any;
let queryData: any;
let categoryData: any;
let userData2: any;
let categoryData2: any;
let deleteRecipeResponse: any;


async function postUser(firstNameToPost:string, lastNameToPost:string, usernameToPost:string, emailToPost:string, passwordToPost:string) {
    try {
      const result = await axios.post('/createUser', {
        first_name: firstNameToPost,
        last_name: lastNameToPost,
        username: usernameToPost,
        email: emailToPost,
        password: passwordToPost
      })
      .then((response: any) => {
        console.log(response);
        userID = response.data;
          if (userID === parseInt(userID, 10)) {
            return userID;
          } else {
            return -1;
          }
      });
      console.log(result);
    } catch(e) {
      console.log(e);
    };
}

async function postUserReturn(firstNameToPost:string, lastNameToPost:string, usernameToPost:string, emailToPost:string, passwordToPost:string){
  await postUser(firstNameToPost, lastNameToPost, usernameToPost, emailToPost, passwordToPost);
  console.log(10)
  return userID;
}

async function getUser(userID : number) {
  try {
    const response = await axios.get('/user/' + userID)
    .then((result: any) => {
      userData = JSON.stringify(result.data);
      return userData;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

async function deleteUser(userID: number){
  try {
    const response = await axios.post('/deleteUser', {
      userID: userID
    })
    .then((result: any) => {
      deleteResponse = result.data;
      return deleteResponse;
    })
    console.log(response);
  } catch (error) {
    console.log(error)
  }
}

async function deleteUserReturn(userID: number){
  await deleteUser(userID);
  return deleteResponse;
}

async function getUserReturn(userID : number) {
  //let userIDString = '' + userID;
  await getUser(userID);  
  return userData;
}

async function deleteRecipe(recipeID : number){
  try {
    const response = await axios.post('/deleteRecipe', {
      recipeID: recipeID
    })
    .then((result: any) => {
      deleteRecipeResponse = result.data;
      return deleteRecipeResponse;
    })
    console.log(response);
  } catch (error) {
    console.log(error)
  }
}

async function deleteRecipeReturn(recipeID: number){
  await deleteRecipe(recipeID);
  return deleteRecipeResponse;
}

async function getUser2(userID : number) {
  try {
    const response = await axios.get('/user/' + userID)
    .then((result: any) => {
      userData2 = JSON.stringify(result.data);
      return userData2;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}


//ikke async
function getUserReturnNoWait(userID : number) {
  getUser2(userID);
  return userData2;
 }

 

async function getRecipe(recipeID: string){
  try {
    const response = await axios.get('/recipe/' + recipeID)
    .then((result: any) => {
      recipeData = JSON.stringify(result.data);
      return recipeData;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}


function getRecipeReturn(recipeID: string){
    getRecipe(recipeID);
    return recipeData;
}
async function getRecipeFromUser(userID: string){
  try {
    const response = await axios.get('/getUserRecipes/' + userID)
    .then((result: any) => {
      userRecipedata = result.data;
      return userRecipedata;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

function getRecipesFromUserReturn(userID: string) {
  getRecipeFromUser(userID);
  return userRecipedata;
}


async function loginUser(username:string, password:string){
  try {
    const response = await axios.post('/user', {
      username: username, 
      password: password
    }) 
    .then((result: any) => {
        userID = result.data;
        if (userID === parseInt(userID, 10)) {
          return userID;
        } else {
          return -1;
        }
      });
    console.log(response);
    //return 1;
  } catch(e) {
    console.log(e);
  }
}

async function loginReturn(username:string, password:string){
  await loginUser(username, password);
  return userID;
}

async function UpdateProfile(idToPost: number, firstNameToPost:string, lastNameToPost:string, usernameToPost:string, emailToPost:string, passwordToPost:string) {
  console.log("wiwo2");
  try {
    console.log("wiwo");
    await axios.post('/updateProfile', {
      userid: idToPost,
      first_name: firstNameToPost,
      last_name: lastNameToPost,
      username: usernameToPost,
      email: emailToPost,
      password: passwordToPost
    });
  } catch(e) {
    console.log("hei3");
    console.log(e);
  }; 
}

async function GetUserFromID(key: Number){
  const user = await axios.get('/getuserfromid/' + {key})
  .then((result: any) => {
    userData = JSON.stringify(result);
    return userData;
  })
}

async function getRecipes(owner: string){
  try {
    const response = await axios.get('/recipe/' + {owner})
    .then((result: any) => {
      userData = JSON.stringify(result.data);
      return userData;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

function postRecipe(ownerIDToPost:string, titleToPost:string, imageToPost:File, timeEstimateToPost:string, categoriesToPost:string, preparationToPost:string, ingredientsToPost:string) {
  const form = new FormData();
  form.append('owner_id', ownerIDToPost)
  form.append('title',titleToPost)
  form.append('image',imageToPost)
  form.append('time_estimate', timeEstimateToPost)
  form.append('preparation', preparationToPost)
  form.append('ingredients', ingredientsToPost)
  form.append('scoreOfRecipe', '0')
  form.append('category', categoriesToPost)
  axios.post('/postRecipe', form)
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {
    console.log(error);
  });
}

async function getRecipiesFromCategory(category:string) {
  try {
    const response = await axios.get('/category=' + category);
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

function postComment(commentToPost:string, ownerIDToPost:number, recipeIDToPost:number) {
  axios.post('/recipe', {
    content: commentToPost,
    owner: ownerIDToPost,
    belongTo: recipeIDToPost, 
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {

    console.log(error);
  });
}

function postLike(ownerIDToPost:number, recipeIDToPost:number) {
  axios.post('/recipe', {
    userThatLikes: ownerIDToPost,
    recipeThatIsLiked: recipeIDToPost, 
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {

    console.log(error);
  });
}

function postScore(ownerIDToPost:number, scoreValue:number ,recipeIDToPost:number) {
  axios.post('/recipe', {
    owner: ownerIDToPost,
    value: scoreValue,
    recipe: recipeIDToPost, 
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {

    console.log(error);
  });
}

function postFavorite(ownerIDToPost:number, recipeIDToPost:number) {
  axios.post('/recipe', {
    userThatFavorites: ownerIDToPost,
    recipeThatIsFavorited: recipeIDToPost, 
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {

    console.log(error);
  });
}

function postFollower(followerIDToPost:number, followedIDToPost:number) {
  axios.post('/profile', {
    userThatFollows: followerIDToPost,
    userGetsFollowed: followedIDToPost, 
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {
    console.log(error);
  });
}

function postCategory(categoryToPost:string) {
  axios.post('/', {
    name: categoryToPost
  })
  .then(function (response: any) {
    console.log(response);
  })
  .catch(function (error: any) {
    console.log(error);
  });
}

async function getQuery(query: string){
  try {

    const response = await axios.get('/search/' + query)
    .then((result: any) => {

      queryData = JSON.stringify(result.data);

      return queryData;
    })
  } catch (error) {
    console.log(error);
  }
}


async function getRecipeFromCategory(category: string){
  try {
    const response = await axios.get('/filter/' + category)
    .then((result: any) => {

      categoryData = result.data;
      return categoryData;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

 function getRecipeFromCategoryReturn(category: string) {
  getRecipeFromCategory(category);
  return categoryData;
}

async function getRecipeFromCategory2(category: string){
  try {
    const response = await axios.get('/filter/' + category)
    .then((result: any) => {

      categoryData2 = result.data;
      return categoryData2;
    })
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

 async function getRecipeFromCategoryReturn2(category: string) {
  await getRecipeFromCategory(category);
  return categoryData2;
}

async function getRecipeReturnAsync(recipeID : string) {
  await getRecipe(recipeID);
  return recipeData;
}


export {getRecipeReturnAsync, UpdateProfile, getRecipeFromCategoryReturn2, deleteUser, deleteUserReturn, deleteRecipeReturn, getRecipeFromCategoryReturn, getRecipesFromUserReturn, getQuery, getRecipeFromCategory, getUserReturnNoWait, getRecipeReturn, getRecipeFromUser,getRecipes, getRecipe, postUser, postUserReturn, getUser, getUserReturn, postRecipe, postComment, postLike, postScore, postFavorite, postFollower, postCategory, loginUser, loginReturn }