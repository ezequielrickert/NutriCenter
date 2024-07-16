import React, { useState, useEffect } from 'react';
import axios from 'axios';
import MonthlyHistory from './monthlyHistory';
import MealHistory from "./mealHistory";
import WeightHistory from "./weightHistory";
import './styles.css';
import {useParams} from "react-router-dom"; // Import the CSS file for styling

const ClientHistory = () => {
    const { customerName } = useParams();

    console.log("Customer Name from Params:", customerName);

    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');

    const userRole = localStorage.getItem('role');


    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", {username, token});
                if (response.data === "User is valid" && userRole === "customer") {
                    setIsValidUser(true);
                }
                else if(userRole === "nutritionist"){
                    /* aca debe chequear si es nutricionista si esta subscripto al usuario correspondiente */
                    const data = {nutritionist : username, customer : customerName};
                    const response = await axios.get("http://localhost:8080/subscribe", {params: data});
                    if (response.data) {
                        setIsValidUser(true);
                    }
                } else {
                    window.location.href = '/universalLogin';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/universalLogin';
            }
        };

        validateUser();
    }, [token, username, userRole]);

    if (!isValidUser) {
        return null;
    }

    return (
        <div className="client-history-container">
            <h1>Client History</h1>
            <div className="scrollable-container">
                <div className="components-container">
                    <div className="component">
                        <h2>Monthly History</h2>
                        <MonthlyHistory customerName={customerName}/>
                    </div>
                    <div className="component">
                        <h2>Weight History</h2>
                        <WeightHistory customerName={customerName}/>
                    </div>
                </div>
                <div className="bottom-component">
                    <h2>Meal History</h2>
                    <MealHistory customerName={customerName}/>
                </div>
            </div>
        </div>
    );
}

export default ClientHistory;
