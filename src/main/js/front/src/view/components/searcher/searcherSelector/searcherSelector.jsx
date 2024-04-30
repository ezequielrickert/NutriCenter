import React, { useState } from 'react';
import Footer from "../../../components/footer";
import {Link} from "react-router-dom";

const SearcherSelector = () => {

    return (
        <div style={{marginBottom: '20px'}}>
            <h1>Choose one to edit</h1>
            <Link to="/searchIngredientHome">
                <button>INGREDIENT SEARCHER</button>
            </Link>
            <Link to="/searchRecipeHome">
                <button>RECIPE SEARCHER</button>
            </Link>
            <Footer />
        </div>
    );
}
export default SearcherSelector;