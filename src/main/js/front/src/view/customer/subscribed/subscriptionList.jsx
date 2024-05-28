import React, { useEffect, useState } from 'react';
import axios from 'axios';

const CustomerSubscriptionList = () => {
    const [nutritionists, setNutritionists] = useState([]);
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && (userRole === "customer")) {
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
        const fetchSubscriptions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/subscription/customer/${username}`);
                setNutritionists(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchSubscriptions();
    }, []);

    return (
        <div>
            <h1>Subscribed Nutritionists</h1>
            <ul>
                {nutritionists.map((nutritionist) => (
                    <li key={nutritionist.nutritionistName}>{nutritionist}</li>
                ))}
            </ul>
        </div>
    );
};

export default CustomerSubscriptionList;