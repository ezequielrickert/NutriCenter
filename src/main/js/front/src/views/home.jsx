import React from 'react';
import { Link } from 'react-router-dom';
import './home.css';

function Home() {
    return (
        <div className="home-container">
            <h1>Welcome to NutriCenter!</h1>
            <p>Press the button below to login</p>
            <Link to="/login">
                <button>Login to Account</button>
            </Link>
            <p>Don't have an account?</p>
            <Link to="/signUp">
                <button>Sign Up!</button>
            </Link>
        </div>
    );
}

export default Home;