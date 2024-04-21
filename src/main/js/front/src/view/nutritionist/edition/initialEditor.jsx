import React, { useState } from 'react';
import NutritionistCreateRecipe from '../edition/recipeActions/nutritionistCreateRecipe';
import NutritionistUpdateRecipe from '../edition/recipeActions/nutritionistUpdateRecipe';
import NutritionistDeleteRecipe from '../edition/recipeActions/nutritionistDeleteRecipe';

const NutritionistRecipeEditor = () => {
    const [operation, setOperation] = useState('');

    const handleSelectChange = (event) => {
        setOperation(event.target.value);
    }

    let OperationComponent;
    switch(operation) {
        case 'create':
            OperationComponent = NutritionistCreateRecipe;
            break;
        case 'update':
            OperationComponent = NutritionistUpdateRecipe;
            break;
        case 'delete':
            OperationComponent = NutritionistDeleteRecipe;
            break;
        default:
            OperationComponent = null;
    }

    return (
        <div>
            <h1>Choose your operation</h1>
            <select value={operation} onChange={handleSelectChange}>
                <option value="">--Please choose an option--</option>
                <option value="create">Create</option>
                <option value="update">Update</option>
                <option value="delete">Delete</option>
            </select>
            {OperationComponent && <OperationComponent />}
        </div>
    );
}

export default NutritionistRecipeEditor;