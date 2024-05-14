import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import {Link} from "react-router-dom";

const DashboardNutritionist = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
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

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container">
            <header className="text-center my-5">
                <h1>Welcome to the Nutritionist Dashboard</h1>
                <Link to={`/nutritionistProfile/${username}`}>
                    <button>Go to Nutritionist Profile</button>
                </Link>
                { /*add dashboard content here*/ }
                <Footer />
            </header>
        </div>
    );
}

export default DashboardNutritionist;