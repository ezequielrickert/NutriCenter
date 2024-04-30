import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Link, useParams } from 'react-router-dom';
import Footer from '../../footer';

const IngredientResult = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { ingredientName } = useParams();
    const [ingredients, setIngredients] = useState([]);
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

        const fetchIngredients = async () => {
            const results = await axios.get(`http://localhost:8080/ingredients/search/${ingredientName}`);
            setIngredients(results.data);
        };

        fetchIngredients();
    }, [ingredientName, isValidUser]);

    if (!isValidUser) {
        return null;
    }

    const handleSearchAgainClick = () => {
        navigate('/searchIngredientHome');
    };

    return (
        <div>
            <h1>Ingredient Results</h1>
            {ingredients.length > 0 ? (
                <ul>
                    {ingredients.map((ingredient) => (
                        <li key={ingredient.ingredientId}>
                            <Link to={`/ingredientInfo/${ingredient.ingredientName}`}>{ingredient.ingredientName}</Link>
                        </li>
                    ))}
                    <button onClick={handleSearchAgainClick}>Search Another Ingredient</button>
                </ul>
            ) : (
                <div>
                    <p>It doesn't exist ingredients named: {ingredientName}</p>
                    <Link to="/searchIngredientHome">
                        <button>Back to Searcher</button>
                    </Link>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default IngredientResult;