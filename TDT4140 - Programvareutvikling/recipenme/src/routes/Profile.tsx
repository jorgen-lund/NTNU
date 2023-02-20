import * as React from 'react';
import "./styleProfile.css"
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import ProfileRecipeFeed from './ProfileRecipeFeed';
import { ConnectedTvOutlined, ConstructionOutlined, LocalActivityTwoTone, ResetTvOutlined } from '@mui/icons-material';
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect } from 'react';
import { getRecipe, getRecipeReturn, getRecipesFromUserReturn, getUser, getUserReturn, getUserReturnNoWait,getRecipeFromUser } from '../client';
import { convertTypeAcquisitionFromJson, isReturnStatement } from 'typescript';
import ProfileRecipes from './ProfileRecipeFeed';
import TitlebarImageList from '../components/molecules/imagelistTest';
import FormButton from '../components/atoms/FormButton';
import { createTheme, ThemeProvider } from '@mui/material';
const axios = require('axios').default;
axios.defaults.baseURL = 'http://127.0.0.1:8000/app1';


let itemdata: Array<any>;

// Source for TabPanelProps interface, TabPanel function is:
// https://mui.com/components/tabs/#BasicTabs.tsx

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}
  
function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;
  
    return (
        <div>
            {
            value === index && (
            <h1>{children}</h1>
            )
            }
        </div>
    );
}


async function SetName() {
    console.log('jeg kjører');
    let userData: any;
    async function GetUserData() {
        const loggedInUser: any = localStorage.getItem('user');
        if (loggedInUser > -1 && loggedInUser != null) {
            const userID = loggedInUser;
            userData = await getUserReturn(userID);
    }

    userData = await GetUserData();
    useEffect(() => {
        console.log(userData);
        const objList = JSON.parse(userData);
        const userFirstName = objList.first_name;
        const userLastName = objList.last_name;
        const newName = userFirstName + ' ' + userLastName;

        let name = document.getElementById('name')!;
        name.innerHTML = newName;
    }, [])
    }
}
  function userRecipes(liste: any) {
     
    let userID = Number(localStorage.getItem('user'));
    let recipedata =  liste;
       console.log('Test disse greiene');
       console.log(recipedata);
       itemdata = [];
       for( let i=0; i < Object.keys(recipedata).length; i++){
              console.log(recipedata[i].title);
              let myRecipe = recipedata[i];
              let image = 'http://127.0.0.1:8000' + myRecipe.image;
              let username = '@' + myRecipe.username;
              let tittel = myRecipe.title;
             itemdata.push({img: image, title: tittel, author: username, recipeid: myRecipe.id,});
          }

        return itemdata;
    
 }




export default function Profile(){
    /* Handlers for tab */
    let navigate = useNavigate(); 
    const [value, setValue] = React.useState(0);
    const [data, setData] = React.useState<JSX.Element>();
    const [userData, setUserData] = React.useState<JSX.Element>();

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
      setValue(newValue);
    };
    let location = useLocation();
    const handleChangeIndex = (index: number) => {
        setValue(index);
    };
    const url = window.location.pathname;
   
    useEffect(() => {
        console.log('oppdatert på navigate')
    try {
        axios.get('/getUserRecipes/' + localStorage.getItem('user').toString())
        .then((result: any) => {
            console.log(result.data)
          setData(TitlebarImageList(userRecipes(result.data)));
        })
      } catch (error) {
        console.log(error);
      }
    }, [navigate]);
    /* Handlers for new-recipe button 
    source: https://stackoverflow.com/questions/50644976/react-button-onclick-redirect-page
    */
    


    const routeChange = () =>{ 
      let path = `/new-recipe`; 
      navigate(path);
    }

    /**
        <div>
            <IconButton aria-label="add_circle" color="secondary" onClick={routeChange}>
                <AddIcon />
            </IconButton>
        </div>
     */

        const theme = createTheme({
            components: {
              // Name of the component
              MuiSvgIcon: {
                styleOverrides: {
                    fontSizeSmall: {
                        fontSize: '2.2vw',
                    },
                },
              },
              MuiTabs: {
                styleOverrides: {
                    flexContainer: {
                        width: '80vw',
                        margin: 'auto',
                        marginTop: '1vw',
                        padding: '0px',
                    },
                    indicator: {
                        backgroundColor: '#76af67',
                    }
                },
              },
              MuiButtonBase: {
                styleOverrides: {
                    root: {
                        padding: '0px',
                        margin: '0px',
                        minWidth: 'none',
                    },
                }
              },
              MuiTab: {
                styleOverrides: {
                  // Name of the slot
                  root: {
                    maxWidth: '10vw',
                    minWidth: '10vw',
                    width: '10vw',
                    fontSize: '1.2vw',
                    margin: '0px',
                    padding: '1vw 0.5vw',
                  },
                  textColorSecondary: {
                    // Some CSS
                    color: '#000000',
                    "&.Mui-selected": {
                        color: '#76af67',
                      }
                  },
                },
              },
            },
          });

    function createUserData(dict: any){
        return(
            <div className="NewRecipe">
                <div id="userinfo">
                    <h1 className = 'ProfileUserName'>Logged in as {dict.username}</h1>
                </div>

                <div id="pluss" onClick={routeChange}>
                    <FormButton label={'Create recipe'} id={'submitRecipe'} handleClick={routeChange}></FormButton>
                </div>

            </div>
        );
    }  
    useEffect(() => {
        console.log('oppdatert på url')
    try {
        axios.get('/user/' + localStorage.getItem('user').toString())
        .then((result: any) => {
          setUserData(createUserData(result.data));
        })
      } catch (error) {
        console.log(error);
      }
    }, [url]);
    return (
        <div className="tabContainer">
            <div className="Biography">
                <div className="UserName"> 
                    <h1 id='name' onLoad={SetName}></h1>
                </div>
                {userData}
            </div>
            <div className="tabs">
                <Box sx={{ width: '100%' }}>
                    <ThemeProvider theme={theme}>
                        <Tabs 
                        value={value} 
                        onChange={handleChange} 
                        centered
                        textColor="secondary"
                        indicatorColor="secondary"
                        >
                            <Tab label="My recipes" />
                        </Tabs>
                        <TabPanel value={value} index={0}>
                        {data}
                        </TabPanel>
                        
                        <TabPanel value={value} index={1}>
                        
                        </TabPanel>
                    </ThemeProvider>

                </Box>
            </div>
        </div>
    );
}



const myRecipeData = [
    {
      img: 'https://images.unsplash.com/photo-1551963831-b3b1ca40c98e',
      title: 'Breakfast',
      author: '@bkristastucchio',
      // rows: 6,
      // cols: 6,
      // featured: true,
    }, 
    {
        img: 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d',
        title: 'Burger',
        author: '@rollelflex_graphy726',
      },
      {
        img: 'https://images.unsplash.com/photo-1589118949245-7d38baf380d6',
      title: 'Bike',
      author: '@southside_customs',
      },
    ]
