import React from 'react';
import ReactDOM from 'react-dom';
import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import './style.css';
import Profile from './routes/Profile';
import Home from './routes/Home';
import reportWebVitals from './reportWebVitals';
import NavBar from './routes/Navbar';
import LoginForm from './components/molecules/LoginForm';
import NewRecipeForm from './components/molecules/NewRecipeForm';
import SignUpForm from './components/molecules/SignUpForm';
import RecipePage from './components/molecules/RecipePage';
import SettingsPage from './components/molecules/SettingsPage';
import EditProfile from './components/molecules/EditProfile';


ReactDOM.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<NavBar />}>
        <Route path="profile" element={<Profile />} />
        <Route path="" element={<Home />} />
        <Route path="profile" element={<Profile />} />
        <Route path="login" element={<LoginForm />} />
        <Route path="signup" element={<SignUpForm />} />
        <Route path="new-recipe" element={<NewRecipeForm />} />
        <Route path="new-recipe" element={<NewRecipeForm />} />
        <Route path="recipe" element={<RecipePage />} />
        <Route path="settings" element={<SettingsPage />} />
        <Route path="edit-profile" element={<EditProfile />} />
        <Route path="*"
        element=
        {
          <main style={{ padding: "1rem" }}>
            <p>There's nothing here!</p>
          </main>
        } />
      </Route>
      
    </Routes>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
