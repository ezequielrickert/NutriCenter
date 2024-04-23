import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import Footer from '../footer';

const IngredientResult = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { ingredientName } = useParams();
    const [ingredients, setIngredients] = useState([]);

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

    return (
        <div className="container">
            <h1 className="text-center my-5">Ingredient Results</h1>
            {ingredients.length > 0 ? (
                <ul className="list-group">
                    {ingredients.map((ingredient) => (
                        <li key={ingredient.ingredientId} className="list-group-item">
                            <Link to={`/ingredientInfo/${ingredient.ingredientName}`}>{ingredient.ingredientName}</Link>
                        </li>
                    ))}
                </ul>
            ) : (
                <div className="alert alert-warning" role="alert">
                    <p>No existen ingredientes que contengan: {ingredientName}</p>
                    <Link to="/searchIngredientHome" className="btn btn-primary">Volver al buscador</Link>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default IngredientResult;