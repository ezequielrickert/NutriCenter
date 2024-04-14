import React from 'react';
import { Link } from 'react-router-dom';

const InitialEditor = () => {
    return (
        <div>
            <h1>Welcome Super Admin!</h1>
            <h2>Choose one to edit</h2>
            <Link to="/ingredientEditor">
                <button>INGREDIENT EDITOR</button>
            </Link>
            <Link to="/recipeEditor">
                <button>RECIPE EDITOR</button>
            </Link>
        </div>
    );
}

export default InitialEditor;