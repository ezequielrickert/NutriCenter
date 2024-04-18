import React, {useEffect, useState} from 'react';
import axios from 'axios';

const DeleteRecipe = () => {
    const [recipes, setRecipes] = useState([]);
    const [selectedRecipe, setSelectedRecipe] = useState();
    const recipeData = {
        recipe: selectedRecipe
    }

    useEffect(() => {
        axios.get('http://localhost:8080/recipes')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setRecipes(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();
        // Display a confirmation popup
        if (window.confirm('Are you sure you want to delete this ingredient?')) {
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
                <input type="submit" value="Delete Ingredient" disabled={!selectedRecipe}/>
            </form>
        </div>
    );
}

export default DeleteRecipe;