import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useParams, Link } from 'react-router-dom';
import Footer from "../../../components/footer";
import '../searchRecipe/searchRecipe.css';

const UserResult = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [nutritionists, setNutritionists] = useState([]);
    const { nutritionistName } = useParams();
    const navigate = useNavigate();

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
        if (!isValidUser) {
            return;
        }

        const fetchNutritionist = async () => {
            try {
                const results = await axios.get(`http://localhost:8080/nutritionistFill/${nutritionistName}`);
                setNutritionists(results.data);
            } catch (error) {
                console.error("Error fetching recipes", error);
            }
        };

        fetchNutritionist();
    }, [nutritionists, isValidUser]);

    const handleSearchAgainClick = () => {
        navigate('/searchProfileHome');
    };

    return (
        <div>
            <h1>Nutritionist Results:</h1>
            {nutritionists.length > 0 ? (
                <ul>
                    {nutritionists.map(nutritionist => (
                        <li key={nutritionist.nutritionistId}>
                            <Link to={`/nutritionistProfile/${nutritionist.nutritionistName}`}>
                                {nutritionist.nutritionistName} - {nutritionist.nutritionistName}
                            </Link>
                        </li>
                    ))}
                    <button onClick={handleSearchAgainClick}>Search Another Nutritionist</button>
                </ul>
            ) : (
                <div>
                    <p>It doesn't exist username named: {nutritionistName}</p>
                    <Link to="/searchProfileHome">
                        <button>Back to Searcher</button>
                    </Link>
                </div>
            )}
            <Footer/>
        </div>
    );
}

export default UserResult;