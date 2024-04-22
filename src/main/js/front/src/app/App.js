import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import { NutritionistRecipeEditor, UniversalLogin } from "../view";
import { InitialPage, SignUpDefault } from '../view';
import { DashboardCustomer, DashboardNutritionist, DashboardStore } from '../view';
import { InitialEditor, IngredientEditor, RecipeEditor } from '../view';
import { SearchIngredientPage, IngredientResult, IngredientInfo } from '../view';
import { AccountSettingsPage, Logout } from '../view';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>

                    <Route path="/" element={<InitialPage />} />

                    <Route path="/defaultSignUp" element={<SignUpDefault />} />
                    <Route path="/universalLogin" element={<UniversalLogin />} />

                    <Route path="/dashboardCustomer" element={<DashboardCustomer />} />

                    <Route path="/dashboardNutritionist" element={<DashboardNutritionist />} />
                    <Route path="/nutritionistRecipeEditor" element={<NutritionistRecipeEditor />} />

                    <Route path="/dashboardStore" element={<DashboardStore />} />

                    <Route path="/initialEditor" element={<InitialEditor />} />
                    <Route path="/ingredientEditor" element={<IngredientEditor />} />
                    <Route path="/recipeEditor" element={<RecipeEditor />} />

                    <Route path="/searchIngredientHome" element={<SearchIngredientPage />} />
                    <Route path="/ingredientResult/:ingredientName" element={<IngredientResult />} />
                    <Route path="/ingredientInfo/:ingredientName" element={<IngredientInfo />} />

                    <Route path={"/accountSelection"} element={<AccountSettingsPage />} />
                    <Route path="/logout" element={<Logout />} />

                </Routes>
            </div>
        </Router>
    );
}

export default App;