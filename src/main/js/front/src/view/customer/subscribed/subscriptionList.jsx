import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import { Link } from "react-router-dom";
import './customerSubscriptionList.css';

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
    }, [username]);

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
    }, [username]);

    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Subscribed Nutritionists</h1>
            <div className="card">
                <div className="card-body">
                    <ul className="list-group">
                        {nutritionists.length > 0 ? (
                            nutritionists.map((nutritionist) => (
                                <li className="list-group-item" key={nutritionist.nutritionistId}>
                                    <Link to={`/nutritionistProfile/${nutritionist.nutritionistName}`} className="text-decoration-none text-primary">
                                        {nutritionist.nutritionistName}
                                    </Link>
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No subscribed nutritionists found.</li>
                        )}
                    </ul>
                </div>
            </div>

            <h1 className="text-center mb-4 mt-4">Subscribed Stores</h1>
            <div className="card">
                <div className="card-body">
                    <ul className="list-group">
                        {stores.length > 0 ? (
                            stores.map((store) => (
                                <li className="list-group-item" key={store.storeId}>
                                    <Link to={`/storeProfile/${store.storeName}`} className="text-decoration-none text-primary">
                                        {store.storeName}
                                    </Link>
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No subscribed stores found.</li>
                        )}
                    </ul>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default CustomerSubscriptionList;
