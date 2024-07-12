import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Footer from "../../components/footer";

const StoreProfile = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const { storeId } = useParams();
    const [store, setStore] = useState();
    const [stocks, setStock] = useState([]);
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
                const storeResponse = await axios.get(`http://localhost:8080/store/${storeId}`);
                const storeData = storeResponse.data;
                if (typeof storeData === 'object') {
                    setStore(storeData);
                } else {
                    console.error('Data received from server is not an object');
                }

                console.log('About to access /stock endpoint');
                const stockResponse = await axios.get(`http://localhost:8080/stock/${storeId}`);
                const stockData = stockResponse.data;
                if (Array.isArray(stockData)) {
                    setStock(stockData);
                } else {
                    console.error('Data received from server is not an array');
                }

                console.log('About to access /follow endpoint');
                const subscribedResponse = await axios.get("http://localhost:8080/follow/store", {
                    params: {
                        store: storeData.storeName,
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
    }, [isValidUser, storeId, username]);


    const handleSubscribe = async () => {
        try {
            const response = await axios.put("http://localhost:8080/follow/store", {
                store: store.storeName,
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
            const response = await axios.delete("http://localhost:8080/follow/store", {
                data: {
                    store: store.storeName,
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
            {store && (
                <>
                    <h1 className="text-center my-5">{store.storeName}</h1>
                    <ul className="list-group">
                        <li className="list-group-item">Email: {store.storeMail}</li>
                        <li className="list-group-item">Number: {store.storePhoneNumber}</li>
                    </ul>
                    <h3 className="text-center my-5">Stock:</h3>
                    <div className="horizontal-container">
                        {stocks.map((stock) => (
                            <div key={stock.store.storeName} className="store-item">
                                <ul>
                                    <li>Ingredient: {stock.ingredient.ingredientName}</li>
                                    <li>Quantity: {stock.quantity}</li>
                                    <li>Brand: {stock.id.brand}</li>
                                </ul>
                            </div>
                        ))}
                    </div>
                    {userRole === "customer" && (
                        <button onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}>
                            {isSubscribed ? 'Unfollow' : 'Follow'}
                        </button>
                    )}
                </>
            )}
            <Footer />
        </div>
    );
}

export default StoreProfile;