import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom'; // Ensure Link is imported
import Footer from "../../../components/footer";
import { useNavigate } from 'react-router-dom';
import './searchRecipe.css';

const RecipeInfo = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [recipe, setRecipe] = useState(null);
    const [error, setError] = useState(null);
    const { recipeId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && ((userRole === "nutritionist") || (userRole === "superAdmin") ||
                    (userRole === "customer") || (userRole === "store"))) {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/universalLogin';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/universalLogin';
            }
        };

        validateUser();
    }, [token, username]);

    useEffect(() => {
        if (!isValidUser) {
            return;
        }
        const fetchRecipe = async () => {
            try {
                const result = await axios.get(`http://localhost:8080/recipes/searchId/${recipeId}`);
                setRecipe(result.data);
            } catch (error) {
                console.error("Error fetching recipe", error);
                setError(error);
            }
        };

        fetchRecipe();
    }, [recipeId, isValidUser]);

    if (error) {
        return <div className="container">Error: {error.message}</div>;
    }

    const handleSearchAgainClick = () => {
        navigate('/searchRecipeHome');
    };

    return (
        <div className="container">
            <div className="recipe-info">
                {recipe && (
                    <>
                        <h1 className="recipe-title">{recipe.recipeName}</h1>
                        <p className="recipe-author">Created by: {recipe.recipeUsername}</p>
                        <p className="recipe-description">Description: {recipe.recipeDescription}</p>
                        <h2 className="recipe-ingredients-title">Ingredients</h2>
                        <ul className="recipe-ingredients-list">
                            {recipe.ingredientList && recipe.ingredientList.map(ingredient => (
                                <li key={ingredient.ingredientId} className="recipe-ingredient">
                                    <Link to={`/ingredientInfo/${ingredient.ingredientName}`}>{ingredient.ingredientName}</Link>
                                </li>
                            ))}
                        </ul>
                        <h2 className="recipe-categories-title">Categories</h2>
                        <ul className="recipe-categories-list">
                            {recipe.categoryList && recipe.categoryList.map(category => (
                                <li key={category.id} className="recipe-category">
                                    {category.categoryName}
                                </li>
                            ))}
                        </ul>
                    </>
                )}
                <button onClick={handleSearchAgainClick} className="search-button">
                    Search Another Recipe
                </button>
            </div>
            <Footer />
        </div>
    );
}

export default RecipeInfo;