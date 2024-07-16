import React, { useState } from 'react';
import SignUpCustomer from '../customer/signUp/signUpCustomer';
import SignUpNutritionist from '../nutritionist/signUp/signUpNutritionist';
import SignUpStore from '../store/signUp/signUpStore';
import {Link} from "react-router-dom";

const DefaultSignUp = () => {
    const [userType, setUserType] = useState('');

    const handleSelectChange = (event) => {
        setUserType(event.target.value);
    };

    return (
        <div className="container d-flex flex-column align-items-center" style={{minHeight: '100vh'}}>
            <h1 className="text-center my-5">Choose your User</h1>
            <div className="mb-3" style={{marginTop: '20px'}}>
                <select onChange={handleSelectChange} className="form-select" style={{maxWidth: '300px'}}>
                    <option value="">Select user type</option>
                    <option value="customer">Customer</option>
                    <option value="nutritionist">Nutritionist</option>
                    <option value="store">Store</option>
                </select>
            </div>
            {userType === 'customer' && <SignUpCustomer/>}
            {userType === 'nutritionist' && <SignUpNutritionist/>}
            {userType === 'store' && <SignUpStore/>}
            <div className="text-center mt-4">
                <p>Have an account? <Link to="/universalLogin">Login here</Link></p>
            </div>
        </div>
    );
}

export default DefaultSignUp;
