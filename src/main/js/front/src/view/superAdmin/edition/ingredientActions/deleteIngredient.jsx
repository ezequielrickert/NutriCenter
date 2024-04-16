import React, {useEffect, useState} from 'react';
import axios from 'axios';

const DeleteIngredient = () => {
    const [ingredients, setIngredients] = useState([]);
    const [selectedIngredient, setSelectedIngredient] = useState();
    const ingredientData = {
        ingredient: selectedIngredient
    }

    useEffect(() => {
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setIngredients(data);
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
            axios.post('http://localhost:8080/deleteIngredient', ingredientData)
                .then(res => console.log(res))
                .catch(err => console.log(err))
        }
    }

    return (
        <div>
            <h1>Delete the ingredient below</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="ingredient">Ingredient:</label><br/>
                <select id="ingredient" value={selectedIngredient ? selectedIngredient.ingredientName : ''}
                        onChange={e => setSelectedIngredient(ingredients.find(a => a.ingredientName === e.target.value))}
                        style={{width: '200px'}}>
                    <option value="">Select an ingredient</option>
                    {ingredients.map((ingredient, index) => (
                        <option key={index} value={ingredient.ingredientName}>{ingredient.ingredientName}</option>
                    ))}
                </select><br/>
                <input type="submit" value="Delete Ingredient" disabled={!selectedIngredient}/>
            </form>
        </div>
    );
}

export default DeleteIngredient;