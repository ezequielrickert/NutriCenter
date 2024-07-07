import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './searchRecipe.css';
import { useNavigate, Link } from 'react-router-dom';
import Footer from "../../footer";

const SearchRecipeHome = () => {
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState('All Categories');
    const [searchTerm, setSearchTerm] = useState('');
    const [recipes, setRecipes] = useState([]);
    const navigate = useNavigate();
    const [isSearchDisabled, setIsSearchDisabled] = useState(true);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://localhost:8080/categories');
                const data = JSON.parse(response.data);
                setCategories(data);
            } catch (error) {
                console.error("Error fetching categories", error);
            }
        };

        fetchCategories();
    }, []);

    useEffect(() => {
        setIsSearchDisabled(searchTerm.trim() === '');
    }, [searchTerm]);

    useEffect(() => {
        // Fetch all recipes in alphabetical order when component mounts
        const fetchAllRecipes = async () => {
            try {
                const response = await axios.get('http://localhost:8080/publicRecipes');
                setRecipes(response.data);
            } catch (error) {
                console.error("Error fetching all recipes", error);
            }
        };

        fetchAllRecipes();
    }, []);

    const handleSearch = async () => {
        if (searchTerm.trim() === '') {
            return;
        }

        await performSearch();
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            handleSearch();
        }
    };

    const performSearch = async () => {
        try {
            if (selectedCategory === "All Categories") {
                const response = await axios.get(`http://localhost:8080/publicRecipes/${searchTerm}`);
                setRecipes(response.data);
            } else {
                const response = await axios.get('http://localhost:8080/publicRecipesByDietType', {
                    params: { term: searchTerm, diet: selectedCategory }
                });
                setRecipes(response.data);
            }
        } catch (error) {
            console.error("Error fetching recipes", error);
        }
    };

    const handleResetSearch = () => {
        setSearchTerm('');
        setSelectedCategory('All Categories');
        const fetchAllRecipes = async () => {
            try {
                const response = await axios.get('http://localhost:8080/publicRecipes');
                setRecipes(response.data);
            } catch (error) {
                console.error("Error fetching all recipes", error);
            }
        };

        fetchAllRecipes();
    };

    return (
        <div className="container">
            <div className="search-bar">
                <select id="select" value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
                    <option value="All Categories">All Categories</option>
                    {categories.map((category) => (
                        <option key={category.id} value={category.categoryName}>
                            {category.categoryName}
                        </option>
                    ))}
                </select>
                <input
                    type="text"
                    id="inputField"
                    placeholder="Search In All Categories"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    onKeyPress={handleKeyPress}
                />
                <button
                    className={`search-button ${isSearchDisabled ? 'disabled' : ''}`}
                    onClick={handleSearch}
                    disabled={isSearchDisabled}
                >
                    Search
                </button>
                <button
                    className="search-button"
                    onClick={handleResetSearch}
                >
                    Reset
                </button>
            </div>
            <div className="recipe-list">
                {recipes.length > 0 ? (
                    recipes.map(recipe => (
                        <div key={recipe.recipeId} className="recipe-item">
                            <Link to={`/recipeInfo/${recipe.recipeId}`}>
                                {recipe.recipeName} - {recipe.recipeUsername}
                            </Link>
                        </div>
                    ))
                ) : (
                    <p>No recipes available</p>
                )}
            </div>
            <Footer />
        </div>
    );
};

export default SearchRecipeHome;
