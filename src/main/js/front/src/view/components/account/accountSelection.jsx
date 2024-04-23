import React from 'react';
import { Link } from 'react-router-dom';
import Footer from '../footer';

const AccountSettingsPage = () => {
    return (
        <div className="container">
            <h1 className="text-center my-5">Account Settings Page</h1>
            <div className="d-flex justify-content-center">
                <Link to="/logout" className="btn btn-primary">Logout</Link>
            </div>
            <Footer />
        </div>
    );
}

export default AccountSettingsPage;