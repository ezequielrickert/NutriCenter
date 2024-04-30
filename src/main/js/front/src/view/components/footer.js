import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {

    let userType = localStorage.getItem('role');
    userType = userType.charAt(0).toUpperCase() + userType.slice(1);

    return (
        <div className="fixed-bottom w-100 bg-light text-white text-center p-3">
            {userType === 'SuperAdmin' ? (
                <>
                    <Link to="/initialEditor" className="btn btn-primary m-1">
                        Initial Editor
                    </Link>
                    <Link to="/searcherSelector" className="btn btn-primary m-1">
                        Searcher Selector
                    </Link>
                    <Link to="/accountSelection" className="btn btn-primary m-1">
                        Settings
                    </Link>
                </>
            ) : (
                <>
                    <Link to={`/dashboard${userType}`} className="btn btn-primary m-1">
                        Profile
                    </Link>
                    <Link to="/searcherSelector" className="btn btn-primary m-1">
                        Searcher Selector
                    </Link>
                    {userType === 'Nutritionist' && (
                        <Link to="/nutritionistRecipeEditor" className="btn btn-primary m-1">
                            Recipe Editor
                        </Link>
                    )}
                    <Link to="/accountSelection" className="btn btn-primary m-1">
                        Settings
                    </Link>
                </>
            )}
        </div>
    );
}

export default Footer;