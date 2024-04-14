import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import { InitialPage, LoginDefault, SignUpDefault } from './view';
import { LoginCustomer, SignUpCustomer } from './view';
import { LoginNutritionist, SignUpNutritionist } from './view';
import { LoginStore, SignUpStore } from './view';
import { LoginSuperAdmin, SignUpSuperAdmin, InitialEditor, IngredientEditor, RecipeEditor } from './view';

function App() {
  return (
      <Router>
        <div className="App">
          <Routes>
              <Route path="/" element={<InitialPage />} />

              <Route path="/defaultLogin" element={<LoginDefault />} />
              <Route path="/defaultSignUp" element={<SignUpDefault />} />

              <Route path="/loginCustomer" element={<LoginCustomer />} />
              <Route path="/signUpCustomer" element={<SignUpCustomer />} />

              <Route path="/loginNutritionist" element={<LoginNutritionist />} />
              <Route path="/signUpNutritionist" element={<SignUpNutritionist />} />

              <Route path="/loginStore" element={<LoginStore />} />
              <Route path="/signUpStore" element={<SignUpStore />} />

              <Route path="/loginSuperAdmin" element={<LoginSuperAdmin />} />
              <Route path="/signUpSuperAdmin" element={<SignUpSuperAdmin />} />
              <Route path="/initialEditor" element={<InitialEditor />} />
              <Route path="/ingredientEditor" element={<IngredientEditor />} />
              <Route path="/recipeEditor" element={<RecipeEditor />} />
          </Routes>
        </div>
      </Router>
  );
}

export default App;