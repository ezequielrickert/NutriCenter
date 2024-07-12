import React from 'react';
import { useNavigate } from 'react-router-dom';

const PurchaseFailed = () => {
    const navigate = useNavigate();

    return (
        <div className="container mt-5">
            <div className="jumbotron">
                <h1 className="display-4">Purchase Failed</h1>
                <p className="lead">There was an error, please try again.</p>
                <hr className="my-4" />
                <p>Click the button below to return to the dashboard.</p>
                <button onClick={() => navigate('/dashboardCustomer')} className="btn btn-primary btn-lg">Return to Main Page</button>
            </div>
        </div>
    );
}

export default PurchaseFailed;