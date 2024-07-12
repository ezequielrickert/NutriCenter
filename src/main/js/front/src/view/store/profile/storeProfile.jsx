import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import './StoreProfile.css'; // Import the StoreProfile.css file
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
    const [selectedQuantities, setSelectedQuantities] = useState({});
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const rowsPerPage = 8;

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

                const stockResponse = await axios.get(`http://localhost:8080/stock/${storeId}`);
                const stockData = stockResponse.data;
                if (Array.isArray(stockData)) {
                    setStock(stockData);
                } else {
                    console.error('Data received from server is not an array');
                }

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
                    <h1 className="text-center my-5">{"Store '" + store.storeName + "'"}</h1>
                    <ul className="list-group mb-3">
                        <li className="list-group-item">Email: {store.storeMail}</li>
                        <li className="list-group-item">Number: {store.storePhoneNumber}</li>
                    </ul>
                    {userRole === "customer" && (
                        <button className="btn btn-primary mb-4" onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}>
                            {isSubscribed ? 'Unfollow' : 'Follow'}
                        </button>
                    )}
                    <h3 className="text-center my-5">Catalog</h3>
                    <input
                        type="text"
                        className="form-control mb-3"
                        placeholder="Search ingredients..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                    <div className="table-responsive">
                        <table className="table table-striped">
                            <thead>
                            <tr>
                                <th className="table-header">Ingredient</th>
                                <th className="table-header">Stock</th>
                                <th className="table-header">Brand</th>
                                <th className="table-header">Price</th>
                                {userRole === "customer" && <th className="table-header">Purchase</th>}
                            </tr>
                            </thead>
                            <tbody>
                            {currentStocks.map((stock) => (
                                <tr key={stock.ingredient.ingredientName}>
                                    <td>
                                        <Link to={`/ingredientInfo/${stock.ingredient.ingredientName}`}>
                                            {stock.ingredient.ingredientName}
                                        </Link>
                                    </td>
                                    <td>{stock.quantity}</td>
                                    <td>{stock.id.brand}</td>
                                    <td>${stock.price}</td>
                                    {userRole === "customer" && (
                                        <td>
                                            <QuantitySelector
                                                value={selectedQuantities[stock.ingredient.ingredientName] || 0}
                                                onChange={(quantity) => handleQuantityChange(stock.ingredient.ingredientName, quantity)}
                                                maxQuantity={stock.quantity}
                                            />
                                        </td>
                                    )}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <nav>
                        <ul className="pagination justify-content-center">
                            {pageNumbers.map(number => (
                                <li key={number} className="page-item">
                                    <a onClick={() => paginate(number)} className="page-link">
                                        {number}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </nav>
                    {userRole === "customer" && (
                        <div className="d-flex justify-content-end">
                            <h5>Total amount: ${totalAmount}</h5>
                        </div>
                    )}
                    {userRole === "customer" && (
                        <button className="btn btn-success mt-3" onClick={handlePurchase}
                                disabled={isPurchaseButtonDisabled()}>
                            Purchase
                        </button>
                    )}
                </>
            )}
            <Footer />
        </div>
    );
}

export default StoreProfile;