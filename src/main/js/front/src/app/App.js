import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import { UniversalLogin} from "../view";
import { InitialPage, SignUpDefault } from '../view';
import { SignUpCustomer, DashboardCustomer } from '../view';
import { SignUpNutritionist, DashboardNutritionist, NutritionistRecipeEditor } from '../view';
import { SignUpStore, DashboardStore } from '../view';
import { InitialEditor, IngredientEditor, RecipeEditor } from '../view';
import { SearchIngredientPage, IngredientResult, IngredientInfo } from '../view';
import { AccountSettingsPage, Logout, Delete } from '../view';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>

                    <Route path="/" element={<InitialPage />} />

                    <Route path="/defaultSignUp" element={<SignUpDefault />} />
                    <Route path="/universalLogin" element={<UniversalLogin />} />

                    <Route path="/signUpCustomer" element={<SignUpCustomer />} />
                    <Route path="/dashboardCustomer" element={<DashboardCustomer />} />

                    <Route path="/signUpNutritionist" element={<SignUpNutritionist />} />
                    <Route path="/dashboardNutritionist" element={<DashboardNutritionist />} />
                    <Route path="/nutritionistRecipeEditor" element={<NutritionistRecipeEditor />} />

                    <Route path="/signUpStore" element={<SignUpStore />} />
                    <Route path="/dashboardStore" element={<DashboardStore />} />

                    <Route path="/initialEditor" element={<InitialEditor />} />
                    <Route path="/ingredientEditor" element={<IngredientEditor />} />
                    <Route path="/recipeEditor" element={<RecipeEditor />} />

                    <Route path="/searchIngredientHome" element={<SearchIngredientPage />} />
                    <Route path="/ingredientResult/:ingredientName" element={<IngredientResult />} />
                    <Route path="/ingredientInfo/:ingredientName" element={<IngredientInfo />} />

                    <Route path={"/accountSelection"} element={<AccountSettingsPage />} />
                    <Route path="/logout" element={<Logout />} />
                    <Route path={"/delete"} element={<Delete />} />

                </Routes>
            </div>
        </Router>
    );
}

export default App;