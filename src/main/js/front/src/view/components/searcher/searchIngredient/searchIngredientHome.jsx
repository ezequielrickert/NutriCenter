import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Autosuggest from 'react-autosuggest';
import axios from 'axios';
import './searchIngredient.css';
import Footer from '../../footer';

const SearchIngredientPage = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [searchTerm, setSearchTerm] = useState('');
    const [suggestions, setSuggestions] = useState([]);
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

        const fetchIngredients = async () => {
            if (searchTerm) {
                const results = await axios.get(`http://localhost:8080/ingredients/begins/${searchTerm}`);
                setSuggestions(results.data.map(ingredient => ingredient.ingredientName));
            }
        };

        fetchIngredients();
    }, [searchTerm, isValidUser]);

    const handleSearchChange = (event, { newValue }) => {
        setSearchTerm(newValue.trim());
    };

    const handleSearchClick = () => {
        const trimmedSearchTerm = searchTerm.trim();
        if (trimmedSearchTerm) {
            if (suggestions.length === 1) {
                navigate(`/ingredientInfo/${suggestions[0]}`);
            }
            else {
                navigate(`/ingredientResult/${trimmedSearchTerm}`);
            }
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
            <h1 className="title">Ingredient Searcher:</h1>
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
}

export default SearchIngredientPage;