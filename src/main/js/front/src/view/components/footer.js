import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {

    const userType = localStorage.getItem('userType');

    return (
        <div style={{ position: 'fixed', left: 0, bottom: 0, width: '100%', backgroundColor: 'lightgray', color: 'white', textAlign: 'center' }}>
            <Link to={`/dashboard${userType}`}>
                <button>Profile</button>
            </Link>
            <Link to="/searchIngredientHome">
                <button>Ingredient Searcher</button>
            </Link>
            <Link to="/accountSelection">
                <button>Settings</button>
            </Link>
        </div>
    );
}

export default Footer;