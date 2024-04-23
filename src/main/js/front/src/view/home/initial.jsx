import React from 'react';
import { Link } from 'react-router-dom';

const InitialPage = () => {
    return (
        <div className="container">
            <h1 className="text-center my-5">Welcome to Nutricenter!</h1>
            <div className="d-flex justify-content-center">
                <Link to="/universalLogin" className="btn btn-primary m-2">Login</Link>
                <Link to="/defaultSignUp" className="btn btn-secondary m-2">Sign Up</Link>
            </div>
        </div>
    );
}

export default InitialPage;