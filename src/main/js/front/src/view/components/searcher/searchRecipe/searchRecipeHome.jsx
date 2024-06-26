import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './searchRecipe.css';
import { useNavigate } from 'react-router-dom';
import Footer from "../../footer";

const SearchRecipeHome = () => {
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState('All Categories');
    const [searchTerm, setSearchTerm] = useState('');
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
        // Habilita o deshabilita el botón de búsqueda según si hay término de búsqueda
        setIsSearchDisabled(searchTerm.trim() === '');
    }, [searchTerm]);

    const handleSearch = async () => {
        if (searchTerm.trim() === '') {
            return; // Evita la búsqueda si el término está vacío
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
        if (selectedCategory === "All Categories") {
            const response = await axios.get(`http://localhost:8080/publicRecipes/${searchTerm}`);
            const recipes = response.data;
            navigate(`/recipeResult/${searchTerm}`, { state: { recipes } });
        } else {
            try {
                const response = await axios.get('http://localhost:8080/publicRecipesByDietType', {
                    params: { term: searchTerm, diet: selectedCategory }
                });
                const recipes = response.data;
                navigate(`/recipeResult/${searchTerm}`, { state: { recipes } });
            } catch (error) {
                console.error("Error fetching recipes", error);
            }
        }
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
            </div>
            <button onClick={() => navigate('/searcherSelector')}>Search Another Recipe</button>
            <Footer />
        </div>
    );
};

export default SearchRecipeHome;
