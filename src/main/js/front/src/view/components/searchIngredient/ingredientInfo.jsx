import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Footer from '../footer';

const IngredientInfo = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { ingredientName } = useParams();
    const [ingredient, setIngredient] = useState(null);

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

    if (!ingredient) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container">
            <h1 className="text-center my-5">{ingredient.ingredientName}</h1>
            <h3 className="text-center">Content per 100 grams</h3>
            <ul className="list-group">
                <li className="list-group-item">Proteins: {ingredient.proteins}</li>
                <li className="list-group-item">Sodium: {ingredient.sodium}</li>
                <li className="list-group-item">Calories: {ingredient.calories}</li>
                <li className="list-group-item">Total Fat: {ingredient.totalFat}</li>
                <li className="list-group-item">Cholesterol: {ingredient.cholesterol}</li>
                <li className="list-group-item">Total Carbohydrate: {ingredient.totalCarbohydrate}</li>
            </ul>
            <Footer />
        </div>
    );
}

export default IngredientInfo;