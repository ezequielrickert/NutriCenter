import React from 'react';
import { Link } from 'react-router-dom';

const LoginDefault = () => {
    return (
        <div>
            <h1>Choose your User</h1>
            <Link to="/loginCustomer">
                <button>Customer</button>
            </Link>
            <Link to="/loginNutritionist">
                <button>Nutritionist</button>
            </Link>
            <Link to="/loginStore">
                <button>Store</button>
            </Link>
            <Link to="/loginSuperAdmin">
                <button>SuperAdmin</button>
            </Link>
        </div>
    );
}

export default LoginDefault;