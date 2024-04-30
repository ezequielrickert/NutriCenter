import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Footer from '../../footer';

const IngredientInfo = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { ingredientName } = useParams();
    const [ingredient, setIngredient] = useState(null);
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

        const fetchIngredient = async () => {
            const result = await axios.get(`http://localhost:8080/ingredients/${ingredientName}`);
            setIngredient(result.data);
        };

        fetchIngredient();
    }, [ingredientName, isValidUser]);

    const handleSearchAgainClick = () => {
        navigate('/searchIngredientHome');
    };

    return (
        <div>
            {ingredient && (
                <>
                    <h1>{ingredient.ingredientName}</h1>
                    <h3>Content per 100 grams</h3>
                    <p>Proteins: {ingredient.proteins}</p>
                    <p>Sodium: {ingredient.sodium}</p>
                    <p>Calories: {ingredient.calories}</p>
                    <p>Total Fat: {ingredient.totalFat}</p>
                    <p>Cholesterol: {ingredient.cholesterol}</p>
                    <p>Total Carbohydrate: {ingredient.totalCarbohydrate}</p>
                </>
            )}
            <button onClick={handleSearchAgainClick}>Search Another Ingredient</button>
            <Footer/>
        </div>
    );
}

export default IngredientInfo;