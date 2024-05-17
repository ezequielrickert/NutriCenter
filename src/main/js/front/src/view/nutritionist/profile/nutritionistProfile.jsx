import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const NutritionistProfile = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { nutritionistId } = useParams();
    const [nutritionist, setNutritionist] = useState(null);
    const [recipes, setRecipes] = useState([]);

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

        axios.get(`http://localhost:8080/nutritionist/${nutritionistId}`)
            .then(response => {
                const data = response.data;
                if (typeof data === 'object') {
                    setNutritionist(data);
                } else {
                    console.error('Data received from server is not an object');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, [isValidUser, nutritionistId]);

    useEffect(() => {
        if (!isValidUser || !nutritionist) {
            return;
        }

        axios.get(`http://localhost:8080/recipes/${nutritionist.nutritionistName}`)
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setRecipes(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, [isValidUser, nutritionist]);

    return (
        <div className="container">
            {nutritionist && recipes && (
                <>
                    <h1 className="text-center my-5">{nutritionist.nutritionistName}</h1>
                    <ul className="list-group">
                        <li className="list-group-item">Email: {nutritionist.nutritionistEmail}</li>
                        <li className="list-group-item">Diploma: {nutritionist.educationDiploma}</li>
                    </ul>
                    <h3 className="text-center my-5">Recipes:</h3>
                    <div className="horizontal-container">
                        {recipes.map((recipe) => (
                            <div key={recipe.recipeName} className="store-item">{recipe.recipeName}</div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}

export default NutritionistProfile;