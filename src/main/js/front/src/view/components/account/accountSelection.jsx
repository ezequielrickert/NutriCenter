import React from 'react';
import { Link } from 'react-router-dom';
import Footer from '../footer';

const AccountSettingsPage = () => {
    return (
        <div>
            <h1>Account Settings Page</h1>
            <Link to="/logout">
                <button>Go to Logout</button>
            </Link>
            <Link to="/delete">
                <button>Go to Delete</button>
            </Link>
            <Footer />
        </div>
    );
}

export default AccountSettingsPage;