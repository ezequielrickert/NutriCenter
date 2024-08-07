export { default as InitialPage } from './home/initial';

export { default as UniversalLogin } from './home/universalLogin';
export { default as SignUpDefault } from './home/signUpDefault';

export { default as DashboardCustomer } from './customer/dashboard/dashboardCustomer';
export { default as Inbox } from './customer/inbox/inbox';
export { default as MealTable } from './customer/mealHistory/addMeal';
export { default as MealHistory } from './customer/mealHistory/mealHistory';
export { default as MonthlyHistory } from './customer/mealHistory/monthlyHistory';
export { default as ClientHistory } from './customer/mealHistory/clientHistory';
export { default as WeightHistory } from './customer/mealHistory/weightHistory';

export { default as DashboardNutritionist } from './nutritionist/dashboard/dashboardNutritionist';
export { default as NutritionistRecipeEditor } from './nutritionist/edition/recipeEditor';
export { default as NutritionistProfile } from './nutritionist/profile/nutritionistProfile';
export { default as UserSearcher} from './components/searcher/searchProfile/searchProfileHome';
export { default as UserResult} from './components/searcher/searchProfile/userResult';

export { default as DashboardStore } from './store/dashboard/dashboardStore';
export { default as StoreProfile } from './store/profile/storeProfile';
export { default as StoreSubscribers } from './store/subscribed/subscriptionList';
export { default as StockIngredientEditor } from './store/stock/edition/createIngredient';
export { default as StockEdition } from './store/stock/edition/stockEdition';

export { default as InitialEditor } from './superAdmin/edition/initialEditor';
export { default as IngredientEditor } from './superAdmin/edition/editors/ingredientEdition';
export { default as RecipeEditor } from './superAdmin/edition/editors/recipeEdition';

export { default as SearcherSelector } from './components/searcher/searcherSelector/searcherSelector';
export { default as SearchIngredientPage } from './components/searcher/searchIngredient/searchIngredientHome';
export { default as IngredientResult } from './components/searcher/searchIngredient/ingredientResult';
export { default as IngredientInfo } from './components/searcher/searchIngredient/ingredientInfo';

export { default as SearchRecipePage } from './components/searcher/searchRecipe/searchRecipeHome';
export { default as RecipeResult } from './components/searcher/searchRecipe/recipeResult';
export { default as RecipeInfo } from './components/searcher/searchRecipe/recipeInfo';

export { default as AccountSettingsPage } from './components/account/accountSelection';
export { default as PurchaseSuccess } from './components/purchase/PurchaseSuccess'
export { default as PurchaseFailed } from './components/purchase/PurchaseFailed'
export { default as Logout } from './components/account/logout';

export { default as CustomerSubscriptionList } from './customer/subscribed/subscriptionList';
export { default as NutritionistSubscriptionList } from './nutritionist/subscribed/subscriptionList';

export { default as AddWeight } from './customer/weighthistory/addWeight';

export { default as MercadoPago } from './components/purchase/MercadoPago';