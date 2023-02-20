import * as React from 'react';
import IconButton from '@mui/material/IconButton';
import AccountCircle from '@mui/icons-material/AccountCircle';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import { useNavigate } from "react-router-dom";
import { useEffect } from 'react';

export default function NavbarDropdown() {
  let navigate = useNavigate(); 

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);

    const userID = Number(localStorage.getItem('user'));
    let login = document.getElementById('loginCategory')!;
    let settings = document.getElementById('settingsCategory')!;
    let profile = document.getElementById('profileCategory')!;
    if (userID != null && userID >= 0){
      login.innerHTML = 'Sign out';
      settings.style.display = 'block';
      profile.style.display = 'block';
    } else {
      login.innerHTML = 'Login';
      settings.style.display = 'none';
      profile.style.display = 'none';
    }
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const routeChangeLogin = () =>{ 
    let login = document.getElementById('loginCategory')!;
    if(login.innerHTML == 'Login'){
      let path = '/login'; 
      navigate(path);
    } else {
      localStorage.setItem('user', '-1');
      let path = '';
      navigate(path);
    }
    setAnchorEl(null);
  }

  const routeChangeProfile = () =>{ 
    let path = '/profile'; 
    navigate(path);
    setAnchorEl(null);
  }

  const routeChangeSettings = () => {
    let path = '/settings';
    navigate(path);
    setAnchorEl(null);
  }


  return (
    <div>
        <IconButton 
        className="iconButton"
        size="large"
        aria-label="account of current user"
        aria-controls="menu-appbar"
        aria-haspopup="true"
        onClick={handleMenu}
        color="inherit"
        >
        <AccountCircle />
        </IconButton>
        <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        anchorOrigin={{
            vertical: 'top',
            horizontal: 'right',
        }}
        keepMounted
        transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
        }}
        open={Boolean(anchorEl)}
        onClose={handleClose}
        >
        <MenuItem id = 'profileCategory' onClick={routeChangeProfile}>Profile</MenuItem>
        <MenuItem id = 'settingsCategory' onClick={routeChangeSettings}>Settings</MenuItem>
        <MenuItem id = 'loginCategory' onClick={routeChangeLogin}>Login</MenuItem>
        </Menu>
    </div>
  );
}