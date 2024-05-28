import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import Autosuggest from 'react-autosuggest';
import '../searchRecipe/searchRecipe.css';
import Footer from "../../../components/footer";


const SearchProfileHome = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const [searchTerm, setSearchTerm] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid") {
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

    const fetchNutritionistAndStores = async () => {
        if (searchTerm) {
            const [nutritionistResults, storeResults] = await Promise.all([
                axios.get(`http://localhost:8080/nutritionistFill/${searchTerm}`),
                axios.get(`http://localhost:8080/storeFill/${searchTerm}`) // Assuming this is the endpoint to search stores
            ]);

            const nutritionists = nutritionistResults.data.map(nutritionist => nutritionist.nutritionistName);
            const stores = storeResults.data.map(store => store.storeName);
            setSuggestions([...nutritionists, ...stores]);
        }
    };

    fetchNutritionistAndStores();
}, [searchTerm, isValidUser]);

    const handleSearchChange = (event, { newValue }) => {
        setSearchTerm(newValue.trim())
    };

    const handleSearchClick = () => {
        const trimmedSearchTerm = searchTerm.trim();
        if (trimmedSearchTerm) {
            navigate(`/userResult/${trimmedSearchTerm}`);
        }
    };

    const handleKeyDown = (event) => {
        if (event.keyCode === 13) {
            handleSearchClick();
        }
    };

    const handleClearClick = () => {
        setSearchTerm('');
    };

    const inputProps = {
        placeholder: 'Search...',
        value: searchTerm,
        onChange: handleSearchChange,
        onKeyDown: handleKeyDown
    };

    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container">
            <h1 className="title">Profile Searcher:</h1>
            <div className="search-section">
                <div className="search-container">
                    <Autosuggest
                        suggestions={suggestions.slice(0, 5)} // Limit to 5 suggestions
                        onSuggestionsFetchRequested={({value}) => setSearchTerm(value)}
                        onSuggestionsClearRequested={() => setSuggestions([])}
                        getSuggestionValue={suggestion => suggestion}
                        renderSuggestion={suggestion => <div>{suggestion}</div>}
                        inputProps={inputProps}
                    />
                    {searchTerm && <button className="clear-button" onClick={handleClearClick}>X</button>}
                </div>
                <button className={`search-button ${searchTerm ? '' : 'search-button--disabled'}`}
                        onClick={handleSearchClick}>Search
                </button>
            </div>
            <Footer />
        </div>
    );
};

export default SearchProfileHome;