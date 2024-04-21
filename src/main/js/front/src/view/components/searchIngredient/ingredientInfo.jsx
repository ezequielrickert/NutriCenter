import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Footer from '../footer';

const IngredientInfo = () => {
    const { ingredientName } = useParams();
    const [ingredient, setIngredient] = useState(null);

    useEffect(() => {
        const fetchIngredient = async () => {
            const result = await axios.get(`http://localhost:8080/ingredients/${ingredientName}`);
            setIngredient(result.data);
        };

        fetchIngredient();
    }, [ingredientName]);

    if (!ingredient) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>{ingredient.ingredientName}</h1>
            <p>Proteins: {ingredient.proteins}</p>
            <p>Sodium: {ingredient.sodium}</p>
            <p>Calories: {ingredient.calories}</p>
            <p>Total Fat: {ingredient.totalFat}</p>
            <p>Cholesterol: {ingredient.cholesterol}</p>
            <p>Total Carbohydrate: {ingredient.totalCarbohydrate}</p>
            <Footer />
        </div>
    );
}

export default IngredientInfo;