import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import Footer from '../footer';

const IngredientResult = () => {
    const { ingredientName } = useParams();
    const [ingredients, setIngredients] = useState([]);

    useEffect(() => {
        const fetchIngredients = async () => {
            const results = await axios.get(`http://localhost:8080/ingredients/search/${ingredientName}`);
            setIngredients(results.data);
        };

        fetchIngredients();
    }, [ingredientName]);

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
                </ul>
            ) : (
                <div>
                    <p>No existen ingredientes que contengan: {ingredientName}</p>
                    <Link to="/searchIngredientHome">
                        <button>Volver al buscador</button>
                    </Link>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default IngredientResult;