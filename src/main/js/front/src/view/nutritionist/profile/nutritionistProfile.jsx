import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const NutritionistProfile = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { nutritionistId } = useParams();
    const [nutritionist, setNutritionist] = useState(null);
    const [recipes, setRecipes] = useState([]);
    const [isSubscribed, setIsSubscribed] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && ["nutritionist", "superAdmin", "customer", "store"].includes(userRole)) {
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
        const fetchData = async () => {
            try {
                const nutritionistResponse = await axios.get(`http://localhost:8080/nutritionist/name/${nutritionistId}`);
                const nutritionistData = nutritionistResponse.data;
                if (typeof nutritionistData === 'object') {
                    setNutritionist(nutritionistData);
                } else {
                    console.error('Data received from server is not an object');
                }

                const recipesResponse = await axios.get(`http://localhost:8080/recipes/${nutritionistData.nutritionistName}`);
                const recipesData = recipesResponse.data;
                if (Array.isArray(recipesData)) {
                    setRecipes(recipesData);
                } else {
                    console.error('Data received from server is not an array');
                }

                const subscribedResponse = await axios.get("http://localhost:8080/subscribe", {
                    params: {
                        nutritionist: nutritionistData.nutritionistName,
                        customer: username
                    }
                });
                if (typeof subscribedResponse.data === 'boolean') {
                    setIsSubscribed(subscribedResponse.data);
                } else {
                    console.error('Data received from server is not a boolean');
                }
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        if (isValidUser) {
            fetchData();
        }
    }, [isValidUser, nutritionistId, username]);

    const handleSubscribe = async () => {
        try {
            const response = await axios.post("http://localhost:8080/subscribe", {
                nutritionist: nutritionist.nutritionistName,
                customer: username
            });

            if (response.status === 200) {
                setIsSubscribed(true);
            } else {
                console.error('Failed to subscribe');
            }
        } catch (error) {
            console.error('There was an error!', error);
        }
    };

    const handleUnsubscribe = async () => {
        try {
            const response = await axios.delete("http://localhost:8080/subscribe", {
                data: {
                    nutritionist: nutritionist.nutritionistName,
                    customer: username
                }
            });

            if (response.status === 200) {
                setIsSubscribed(false);
            } else {
                console.error('Failed to unsubscribe');
            }
        } catch (error) {
            console.error('There was an error!', error);
        }
    };

    const handleRecipeClick = (recipe) => {
        if ((isSubscribed && !recipe.isPublic) || recipe.isPublic) {
            navigate(`/recipeInfo/${recipe.recipeId}`);
        }
    };

    const publicRecipes = recipes.filter(recipe => recipe.isPublic);
    const privateRecipes = recipes.filter(recipe => !recipe.isPublic);

    return (
        <div className="container">
            {nutritionist && (
                <>
                    <h1 className="text-center my-5">{nutritionist.nutritionistName}</h1>
                    <ul className="list-group">
                        <li className="list-group-item">Email: {nutritionist.nutritionistEmail}</li>
                        <li className="list-group-item">Diploma: {nutritionist.educationDiploma}</li>
                    </ul>
                    {userRole === "customer" && (
                        <div className="center-content">
                            <button
                                className="btn btn-primary mt-3"
                                onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}
                            >
                                {isSubscribed ? 'Unsubscribe' : 'Subscribe'}
                            </button>
                        </div>
                    )}
                    <h3 className="text-center my-5">Recetas Públicas:</h3>
                    <div className="horizontal-container">
                        {publicRecipes.map((recipe) => (
                            <div
                                key={recipe.recipeId}
                                className="store-item"
                                onClick={() => handleRecipeClick(recipe)}
                                style={{ cursor: 'pointer' }}
                            >
                                {recipe.recipeName}
                            </div>
                        ))}
                    </div>
                    {userRole === "customer" && (
                        <>
                            <h3 className="text-center my-5">Recetas Privadas:</h3>
                            <div className="horizontal-container">
                                {privateRecipes.map((recipe) => (
                                    <div
                                        key={recipe.recipeId}
                                        className="store-item"
                                        onClick={() => handleRecipeClick(recipe)}
                                        style={{ cursor: 'pointer' }}
                                    >
                                        {recipe.recipeName}
                                    </div>
                                ))}
                            </div>
                        </>
                    )}
                </>
            )}
        </div>
    );
}

export default NutritionistProfile;
