import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {

    let userType = localStorage.getItem('role');
    userType = userType.charAt(0).toUpperCase() + userType.slice(1);

    return (
        <div style={{ position: 'fixed', left: 0, bottom: 0, width: '100%', backgroundColor: 'lightgray', color: 'white', textAlign: 'center' }}>
            {userType === 'SuperAdmin' ? (
                <>
                    <Link to="/initialEditor">
                        <button>Initial Editor</button>
                    </Link>
                    <Link to="/searchIngredientHome">
                        <button>Ingredient Searcher</button>
                    </Link>
                    <Link to="/accountSelection">
                        <button>Settings</button>
                    </Link>
                </>
            ) : (
                <>
                    <Link to={`/dashboard${userType}`}>
                        <button>Profile</button>
                    </Link>
                    <Link to="/searchIngredientHome">
                        <button>Ingredient Searcher</button>
                    </Link>
                    {userType === 'Nutritionist' && (
                        <Link to="/nutritionistRecipeEditor">
                            <button>Recipe Editor</button>
                        </Link>
                    )}
                    <Link to="/accountSelection">
                        <button>Settings</button>
                    </Link>
                </>
            )}
        </div>
    );
}

export default Footer;