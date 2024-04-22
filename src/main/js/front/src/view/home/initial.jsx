import React from 'react';
import { Link } from 'react-router-dom';

const InitialPage = () => {
    return (
        <div>
            <h1>Welcome to Nutricenter!</h1>
            <Link to="/universalLogin">
                <button>Login</button>
            </Link>
            <Link to="/defaultSignUp">
                <button>Sign Up</button>
            </Link>
        </div>
    );
}

export default InitialPage;