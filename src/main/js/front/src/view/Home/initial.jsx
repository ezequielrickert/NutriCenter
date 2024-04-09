import React from 'react';
import { Link } from 'react-router-dom';

const InitialPage = () => {
    return (
        <div>
            <h1>Welcome to our site!</h1>
            <Link to="/login">
                <button>Login</button>
            </Link>
            <Link to="/signUp">
                <button>Sign Up</button>
            </Link>
        </div>
    );
}

export default InitialPage;