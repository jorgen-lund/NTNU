import { Box, createTheme, ImageList, Tab, Tabs, ThemeProvider } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import React, { useEffect } from 'react';
import { styled } from '@mui/material/styles';
import './FrontPageTabs.css';
//import { red } from '@material-ui/core/colors';
import FormTextField from '../atoms/FormTextField';
import { getJSDocOverrideTagNoCache } from 'typescript';
import { lineHeight, width } from '@mui/system';
import MainBody from '../../tempComponents/MainBody';
import { getRecipe, getQuery, getRecipeFromCategory, getRecipeFromCategoryReturn, getUserReturnNoWait } from '../../client';
import { useTheme } from '@emotion/react';
import TitlebarImageList from './imagelistTest';
import Container from '@mui/material/Container';
import { Console } from 'console';
const axios = require('axios').default;
axios.defaults.baseURL = 'http://127.0.0.1:8000/app1';

// let breakfastdata: Array<any>;
// let simpledishdata: Array<any>;
// let vegandata: Array<any>;
// let italiandata: Array<any>;
// let glutenfreedata: Array<any>;



async function updateView(newValue: number) {
  if (newValue === 0) {
      let searchbar = document.getElementById('searchbarContainerID');
      if (searchbar != null) {
          searchbar.style.display = "block";
      }

  } else {
      let searchbar = document.getElementById('searchbarContainerID');
      let category = "";

      if (searchbar != null) {
          searchbar.style.display = "none";
      }
      if (newValue === 1) {
          category = "Breakfast";
          

      } else if (newValue === 2) {
          category = "Simple dish";
          

      } else if (newValue === 3) {
          category = "Vegan";
          

      } else if (newValue === 4) {
          category = "Italian";
          

      } else if (newValue === 5) {
          category = "Gluten-free";
      }
  }
}

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

// function breakfastRecipes() {
//     let recipecategorydata = getRecipeFromCategoryReturn('Breakfast');
//     console.log(recipecategorydata);
//     console.log('Test disse greiene');
//     breakfastdata = [];
//     for( let i=0; i < Object.keys(recipecategorydata).length; i++){
//          console.log(recipecategorydata[i].title);
//          let myRecipeBreakfast = recipecategorydata[i];
//          let image = 'http://127.0.0.1:8000' + myRecipeBreakfast.image;
//          let tittel = myRecipeBreakfast.title;
//          let owner = myRecipeBreakfast.owner_id;
//          let owneruser =  getUserReturnNoWait(owner);
//           let myuserdata = JSON.parse(owneruser);
//           let username = '@' + myuserdata.username;
//           breakfastdata.push({img: image, title: tittel, author: username, recipeid: myRecipeBreakfast.id,});
//      }
    
//     return breakfastdata;
// }



function userRecipes(liste: any) {
  let recipedata =  liste;
     console.log('Test disse greiene');
     console.log(recipedata);
  let itemdata = [];
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


let fixen = 0
const FrontPageTabs = () => {
    const [value, setValue] = React.useState(0);
    const [data, setData] = React.useState<JSX.Element>();
    const [fix, setFix] = React.useState(0);
    function handleFix(){
      fixen ++;
      console.log('fix')
      setFix(fixen);
    }
    useEffect(() => {
      let num = value;
      let category: string;
      switch(num){
        case 0: {
          if((document.getElementById('searchbar') as HTMLInputElement).value == ''){
            category = 'getAll'
          }else{
          category = 'search/' + (document.getElementById('searchbar') as HTMLInputElement).value;
          }
          break;
        }

        case 1: {
          category = 'filter/Breakfast';
          break;
        }
        case 2: {
          category = 'filter/Simple dish';
          break;
        }
        case 3: {
          category = 'filter/Vegan';
          break;
        }
        case 4: {
          category = 'filter/Italian';
          break;
        }
        case 5: {
          category = 'filter/Gluten-free';
          break;
        }
      }
  try {
      axios.get('/' + category)
      .then((result: any) => {
        setData(TitlebarImageList(userRecipes(result.data)));
      })
    } catch (error) {
      console.log(error);
    }
  }, [value, fix]);
    const handleChange =  (event: React.SyntheticEvent, newValue: number) => {
        console.log(newValue);

        setValue(newValue);
        updateView(newValue);
    };
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


      const themeIcon = createTheme({
        components: {
          // Name of the component
          MuiSvgIcon: {
            styleOverrides: {
                fontSizeSmall: {
                    fontSize: '2vw',
                    lineHeight: '2.5vw'
                },
                root: {
                    margin: '0px',

                }
            },
          },
        }
      });
      
      

    return (
        <div id="tabID" className="tabs">
            <Box sx={{ width: '100%', color: 'FEFBE9' }}>
                <ThemeProvider theme={theme}>
                    <Tabs value={value} allowScrollButtonsMobile={true} onChange={handleChange} variant='fullWidth' centered textColor="secondary" indicatorColor="secondary">
                        <Tab label={<SearchIcon fontSize='small'></SearchIcon>} />
                        <Tab label="Breakfast" />
                        <Tab label="Simple dish" />
                        <Tab label="Vegan" />
                        <Tab label="Italian" />
                        <Tab label="Gluten-free" />
                    </Tabs>
                    <div id="searchbarContainerID">
                      <input placeholder="Search" id="searchbar" type="text" onChange = {e => handleFix()}/>
                      </div>
                    <TabPanel value={value} index={0}>
                    {data}
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        {data}
                    </TabPanel>
                    <TabPanel value={value} index={2}>
                        
                      {data}
                    </TabPanel>
                    <TabPanel value={value} index={3}>
                        {data}
                    </TabPanel>
                    <TabPanel value={value} index={4}>
                        
                        
                            {data}
                        
                    </TabPanel>
                    <TabPanel value={value} index={5}>
                        {data}
                    </TabPanel>
                </ThemeProvider>
                
            </Box>
            
        </div>
    ); 
};

export default FrontPageTabs;
