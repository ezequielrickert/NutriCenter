import React, {useEffect, useState} from 'react';
import NutritionistCreateRecipe from '../edition/recipeActions/nutritionistCreateRecipe';
import NutritionistUpdateRecipe from '../edition/recipeActions/nutritionistUpdateRecipe';
import NutritionistDeleteRecipe from '../edition/recipeActions/nutritionistDeleteRecipe';
import Footer from "../../components/footer";
import axios from "axios";

const NutritionistRecipeEditor = () => {
    const [operation, setOperation] = useState('');
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const [isPublic, setIsPublic] = useState(true);
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "nutritionist") {
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

    if (!isValidUser) {
        return null;
    }

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
        <div style={{marginBottom: '20px'}}>
            <h1>Choose your operation</h1>
            <select value={operation} onChange={handleSelectChange}>
                <option value="">--Please choose an option--</option>
                <option value="create">Create</option>
                <option value="update">Update</option>
                <option value="delete">Delete</option>
            </select>
            {OperationComponent && <OperationComponent/>}
            <Footer/>
        </div>
    );
}

export default NutritionistRecipeEditor;