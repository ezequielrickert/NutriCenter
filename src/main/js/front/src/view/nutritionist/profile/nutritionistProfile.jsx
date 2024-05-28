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
    const [isSubscribed, setIsSubscribed] = useState(false);


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
                    {userRole === "customer" && (
                        <button onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}>
                            {isSubscribed ? 'Unsubscribe' : 'Subscribe'}
                        </button>
                    )}
                </>
            )}
        </div>
    );
}

export default NutritionistProfile;