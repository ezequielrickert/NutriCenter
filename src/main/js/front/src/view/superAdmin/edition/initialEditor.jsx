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
        <div className="container my-5">
            <h1 className="text-center">Welcome Super Admin!</h1>
            <h2 className="text-center text-secondary mb-4">Choose one to edit</h2>
            <div className="d-flex justify-content-around">
                <Link to="/ingredientEditor" className="btn btn-primary m-2">
                    INGREDIENT EDITOR
                </Link>
                <Link to="/recipeEditor" className="btn btn-primary m-2">
                    RECIPE EDITOR
                </Link>
            </div>
            <Footer />
        </div>
    );
}

export default InitialEditor;