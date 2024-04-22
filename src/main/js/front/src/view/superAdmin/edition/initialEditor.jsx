import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from "axios";
import Footer from "../../components/footer";

const InitialEditor = () => {

    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "superAdmin") {
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
        <div style={{marginBottom: '20px'}}>
            <h1>Welcome Super Admin!</h1>
            <h2>Choose one to edit</h2>
            <Link to="/ingredientEditor">
                <button>INGREDIENT EDITOR</button>
            </Link>
            <Link to="/recipeEditor">
                <button>RECIPE EDITOR</button>
            </Link>
            <Footer />
        </div>
    );
}

export default InitialEditor;