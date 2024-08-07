import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddMeal = () => {
    const [recipes, setRecipes] = useState([]);
    const [selectedMeal, setSelectedMeal] = useState('');
    const [selectedRecipe, setSelectedRecipe] = useState('');
    const username = localStorage.getItem('username');

    useEffect(() => {
        const fetchRecipes = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/publicRecipes`);
                if (Array.isArray(response.data)) {
                    setRecipes(response.data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchRecipes();
    }, []);

    const handleMealChange = (event) => {
        setSelectedMeal(event.target.value);
    };

    const handleRecipeChange = (event) => {
        setSelectedRecipe(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const meal = {
            mealType: selectedMeal,
            recipeId: selectedRecipe,
            username: username
        };

        axios.post('http://localhost:8080/meal', meal)
            .then(response => {
                console.log('Meal added successfully');
                toast.success('Meal added successfully');
            })
            .catch(error => {
                console.error('There was an error adding meal!', error);
                toast.error('There was an error adding the meal!');
            });
    }

    return (
        <div className="container">
            <form onSubmit={handleSubmit} className="mt-5">
                <h2 className="mb-3">Meal</h2>
                <div className="form-group mb-5">
                    <select id="meal" className="form-control" value={selectedMeal} onChange={handleMealChange}>
                        <option value="">Select a meal</option>
                        <option value="Breakfast">Breakfast</option>
                        <option value="Lunch">Lunch</option>
                        <option value="Dinner">Dinner</option>
                    </select>
                </div>
                <h2 className="mb-3">Recipe</h2>
                <div className="form-group mb-5">
                    <select id="recipe" className="form-control" value={selectedRecipe} onChange={handleRecipeChange}>
                        <option value="">Select a recipe</option>
                        {recipes.map((recipe, index) => {
                            console.log('Recipe:', recipe); // Log each recipe
                            return (
                                <option key={index} value={recipe.recipeId}>{recipe.recipeName}</option>
                            );
                        })}
                    </select>
                </div>
                <button type="submit" className="btn btn-primary mt-3">Add Meal</button>
            </form>
            <Footer />
            <ToastContainer />
        </div>
    );
};

export default AddMeal;