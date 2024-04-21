import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Autosuggest from 'react-autosuggest';
import axios from 'axios';
import './SearchIngredientPage.css';
import Footer from '../footer';

const SearchIngredientPage = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchIngredients = async () => {
            if (searchTerm) {
                const results = await axios.get(`http://localhost:8080/ingredients/begins/${searchTerm}`);
                setSuggestions(results.data.map(ingredient => ingredient.ingredientName));
            }
        };

        fetchIngredients();
    }, [searchTerm]);

    const handleSearchChange = (event, { newValue }) => {
        setSearchTerm(newValue.trim());
    };

    const handleSearchClick = () => {
        const trimmedSearchTerm = searchTerm.trim();
        if (trimmedSearchTerm) {
            navigate(`/ingredientResult/${trimmedSearchTerm}`);
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

    return (
        <div className="container">
            <h1 className="title">Buscador de Ingredientes:</h1>
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