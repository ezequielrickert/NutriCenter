import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import '../global.css';
import {
    CustomerSubscriptionList, MercadoPago,
    NutritionistRecipeEditor,
    NutritionistSubscriptionList,
    UniversalLogin
} from "../view";
import { InitialPage, SignUpDefault } from '../view';
import { DashboardCustomer, DashboardNutritionist, DashboardStore } from '../view';
import { InitialEditor, IngredientEditor, RecipeEditor } from '../view';
import { SearcherSelector, SearchIngredientPage, SearchRecipePage } from "../view";
import { IngredientResult, IngredientInfo } from '../view';
import { RecipeResult, RecipeInfo } from '../view';
import { AccountSettingsPage, Logout } from '../view';
import { MealTable, MealHistory, MonthlyHistory, ClientHistory, WeightHistory } from "../view";
import { NutritionistProfile } from "../view";
import { UserSearcher, UserResult } from "../view";
import { StoreProfile } from "../view";
import { AddWeight } from "../view";
import { StockIngredientEditor, StockEdition, StoreSubscribers } from "../view";
import { Inbox } from "../view";

function App() {

    const handleMessagesRead = () => {
        console.log("Messages have been marked as read");
    };

    return (
        <Router>
            <div className="App">
                <Routes>

                    <Route path="/" element={<InitialPage />} />

                    <Route path="/defaultSignUp" element={<SignUpDefault />} />
                    <Route path="/universalLogin" element={<UniversalLogin />} />

                    <Route path="/dashboardCustomer" element={<DashboardCustomer handleMessagesRead={handleMessagesRead} />} />

                    <Route path="/dashboardNutritionist" element={<DashboardNutritionist />} />
                    <Route path="/nutritionistRecipeEditor" element={<NutritionistRecipeEditor />} />
                    <Route path="/nutritionistProfile/:nutritionistId" element={<NutritionistProfile />} />

                    <Route path="/dashboardStore" element={<DashboardStore />} />

                    <Route path="/initialEditor" element={<InitialEditor />} />
                    <Route path="/ingredientEditor" element={<IngredientEditor />} />
                    <Route path="/recipeEditor" element={<RecipeEditor />} />

                    <Route path="/searcherSelector" element={<SearcherSelector />} />
                    <Route path="/searchIngredientHome" element={<SearchIngredientPage />} />
                    <Route path="/searchRecipeHome" element={<SearchRecipePage />} />
                    <Route path="/searchProfileHome" element={<UserSearcher />} />
                    <Route path="/userResult/:nutritionistName" element={<UserResult />} />

                    <Route path="/ingredientResult/:ingredientName" element={<IngredientResult />} />
                    <Route path="/ingredientInfo/:ingredientName" element={<IngredientInfo />} />

                    <Route path="/recipeResult/:recipeName" element={<RecipeResult />} />
                    <Route path="/recipeInfo/:recipeId" element={<RecipeInfo />} />

                    <Route path="/accountSelection" element={<AccountSettingsPage />} />
                    <Route path="/logout" element={<Logout />} />

                    <Route path="/storeIngredientEditor" element={<StockIngredientEditor />} />
                    <Route path="/stockEdition" element={<StockEdition />} />
                    <Route path="/storeSubscribers" element={<StoreSubscribers />} />
                    <Route path="/storeProfile/:storeId" element={<StoreProfile />} />

                    <Route path={"/mealTable"} element={<MealTable />} />
                    <Route path={"/mealHistory"} element={<MealHistory />} />
                    <Route path={"/monthlyHistory"} element={<MonthlyHistory />} />
                    <Route path={"/clientHistory"} element={<ClientHistory />} />
                    <Route path={"/weightHistory"} element={<WeightHistory />} />

                    <Route path={"/customer-subscriptions"} element={<CustomerSubscriptionList />} />
                    <Route path={"/nutritionist-subscriptions"} element={<NutritionistSubscriptionList />} />
                    <Route path={"/addWeight"} element={<AddWeight />} />
                    <Route path={"/inbox"} element={<Inbox />} />

                    <Route path={"/payment/:"} element={<MercadoPago />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;