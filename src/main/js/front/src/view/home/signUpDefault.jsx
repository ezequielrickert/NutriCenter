import React, { useState } from 'react';
import SignUpCustomer from '../customer/signUp/signUpCustomer';
import SignUpNutritionist from '../nutritionist/signUp/signUpNutritionist';
import SignUpStore from '../store/signUp/signUpStore';

const DefaultSignUp = () => {
    const [userType, setUserType] = useState('');

    const handleSelectChange = (event) => {
        setUserType(event.target.value);
    };

    return (
        <div className="container">
            <h1 className="text-center my-5">Choose your User</h1>
            <div className="d-flex justify-content-center mb-3">
                <select onChange={handleSelectChange} className="form-select">
                    <option value="">Select user type</option>
                    <option value="customer">Customer</option>
                    <option value="nutritionist">Nutritionist</option>
                    <option value="store">Store</option>
                </select>
            </div>
            {userType === 'customer' && <SignUpCustomer />}
            {userType === 'nutritionist' && <SignUpNutritionist />}
            {userType === 'store' && <SignUpStore />}
        </div>
    );
}

export default DefaultSignUp;