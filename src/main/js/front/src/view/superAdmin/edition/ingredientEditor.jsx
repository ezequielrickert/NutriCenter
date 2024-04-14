import React, { useState } from 'react';
import CreateIngredient from './createIngredient';
import UpdateIngredient from './updateIngredient';
import DeleteIngredient from './deleteIngredient';

const IngredientEditor = () => {
    const [operation, setOperation] = useState('');

    const handleSelectChange = (event) => {
        setOperation(event.target.value);
    }

    let OperationComponent;
    switch(operation) {
        case 'create':
            OperationComponent = CreateIngredient;
            break;
        case 'update':
            OperationComponent = UpdateIngredient;
            break;
        case 'delete':
            OperationComponent = DeleteIngredient;
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

export default IngredientEditor;