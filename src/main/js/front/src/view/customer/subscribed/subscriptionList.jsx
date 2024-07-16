import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import {Link} from "react-router-dom";

const CustomerSubscriptionList = () => {
    const [nutritionists, setNutritionists] = useState([]);
    const [stores, setStores] = useState([]);
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
        const fetchNutritionistSubscriptions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/NutritionistSubscriptions/customer/${username}`);
                setNutritionists(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchNutritionistSubscriptions();
    }, []);

    useEffect(() => {
        const fetchStoreSubscriptions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/StoreSubscriptions/customer/${username}`);
                setStores(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchStoreSubscriptions();
    }, []);

    return (
        <div>
            <h1>Subscribed Nutritionists</h1>
            <ul>
                {nutritionists.map((nutritionist) => (
                    <li key={nutritionist.nutritionistId}>
                        <Link to={`/nutritionistProfile/${nutritionist.nutritionistName}`}>
                            {nutritionist.nutritionistName}
                        </Link>
                    </li>
                ))}
            </ul>
            <h1>Subscribed Stores</h1>
            <ul>
                {stores.map((store) => (
                    <li key={store.storeId}>
                        <Link to={`/storeProfile/${store.storeName}`}>
                            {store.storeName}
                        </Link>
                    </li>
                ))}
            </ul>
            <Footer />
        </div>
    );
};

export default CustomerSubscriptionList;