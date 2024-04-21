import React, { useEffect, useState } from 'react';
import axios from "axios";
import CreateRecipe from '../recipeActions/createRecipe';
import UpdateRecipe from '../recipeActions/updateRecipe';
import DeleteRecipe from '../recipeActions/deleteRecipe';

const RecipeEditor = () => {
    const [operation, setOperation] = useState('');

    const handleSelectChange = (event) => {
        setOperation(event.target.value);
    }

    let OperationComponent;
    switch(operation) {
        case 'create':
            OperationComponent = CreateRecipe;
            break;
        case 'update':
            OperationComponent = UpdateRecipe;
            break;
        case 'delete':
            OperationComponent = DeleteRecipe;
            break;
        default:
            OperationComponent = null;
    }

    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "superAdmin") {
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

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
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

export default RecipeEditor;