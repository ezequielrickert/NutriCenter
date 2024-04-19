import React from 'react';


const DashboardCustomer = () => {
    const token = localStorage.getItem('token');


    if (!token) {
        window.location.href = '/loginCustomer';
    }


    return (
        <div className="App">
            <header className="App-header">
                <h1>Welcome to the Customer Dashboard</h1>
                { /*add dashboard content here*/ }
            </header>
        </div>
    );
}


export default DashboardCustomer;
