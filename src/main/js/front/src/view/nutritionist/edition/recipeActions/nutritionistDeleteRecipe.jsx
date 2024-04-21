import React, {useEffect, useState} from 'react';
import axios from 'axios';

const NutritionistDeleteRecipe = () => {
    const [recipes, setRecipes] = useState([]);
    const [selectedRecipe, setSelectedRecipe] = useState();
    const recipeData = {
        recipe: selectedRecipe
    }
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid") {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/loginNutritionist';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/loginNutritionist';
            }
        };

        validateUser();
    }, [token, username]);

    useEffect(() => {
        if (!isValidUser) {
            return;
        }

        axios.get(`http://localhost:8080/recipes/${username}`)
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setRecipes(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, [isValidUser]);

    const handleSubmit = (event) => {
        event.preventDefault();
        // Display a confirmation popup
        if (window.confirm('Are you sure you want to delete this recipe?')) {
            axios.post('http://localhost:8080/deleteRecipe', recipeData)
                .then(res => console.log(res))
                .catch(err => console.log(err))
        }
    }

    return (
        <div>
            <h1>Delete the recipe below</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="ingredient">Recipe:</label><br/>
                <select id="recipe" value={selectedRecipe ? selectedRecipe.recipeName : ''}
                        onChange={e => setSelectedRecipe(recipes.find(a => a.recipeName === e.target.value))}
                        style={{width: '200px'}}>
                    <option value="">Select a recipe</option>
                    {recipes.map((recipe, index) => (
                        <option key={index} value={recipe.recipeName}>{recipe.recipeName}</option>
                    ))}
                </select><br/>
                <input type="submit" value="Delete Recipe" disabled={!selectedRecipe}/>
            </form>
        </div>
    );
}

export default NutritionistDeleteRecipe;