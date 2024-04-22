import React from 'react';
import { Link } from 'react-router-dom';

const DefaultSignUp = () => {
    return (
        <div>
            <h1>Choose your User</h1>
            <Link to="/signUpCustomer">
                <button>Customer</button>
            </Link>
            <Link to="/signUpNutritionist">
                <button>Nutritionist</button>
            </Link>
            <Link to="/signUpStore">
                <button>Store</button>
            </Link>
            <div>
                <Link to="/">Go Back</Link> {/* Botón de regreso */}
            </div>
        </div>
    );
}

export default DefaultSignUp;