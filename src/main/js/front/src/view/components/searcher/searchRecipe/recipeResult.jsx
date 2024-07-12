import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams, useLocation, Link } from 'react-router-dom';
import Footer from "../../../components/footer";
import './searchRecipe.css';

const RecipeResult = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { recipeName: searchTerm } = useParams();  // Obtener searchTerm desde la URL
    const location = useLocation();
    const { recipes } = location.state || { recipes: [] };
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
    }, [token, username, searchTerm]);

    const handleSearchAgainClick = () => {
        navigate('/searchRecipeHome');
    };

    return (
        <div>
            <h1>Recipe Results:</h1>
            {recipes.length > 0 ? (
                <ul>
                    {recipes.map(recipe => (
                        <li key={recipe.recipeId}>
                            <Link to={`/recipeInfo/${recipe.recipeId}`}>
                                {recipe.recipeName} - {recipe.recipeUsername}
                            </Link>
                        </li>
                    ))}
                    <button onClick={handleSearchAgainClick}>Search Another Recipe</button>
                </ul>
            ) : (
                <div>
                    <p>It doesn't exist recipes named: {searchTerm}</p>
                    <Link to="/searchRecipeHome">
                        <button>Back to Searcher</button>
                    </Link>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default RecipeResult;
