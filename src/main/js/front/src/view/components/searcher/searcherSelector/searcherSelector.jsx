import Footer from "../../../components/footer";
import {Link} from "react-router-dom";
import React from "react";

const SearcherSelector = () => {

    return (
        <div className="container my-5">
            <h1 className="text-center">Choose Selector</h1>
            <div className="d-flex justify-content-around">
                <Link to="/searchIngredientHome" className="btn btn-primary m-2">
                    INGREDIENT SEARCHER
                </Link>
                <Link to="/searchRecipeHome" className="btn btn-primary m-2">
                    RECIPE SEARCHER
                </Link>
                <Link to= "/searchProfileHome" className="btn btn-primary m-2">
                    PROFILE SEARCHER
                </Link>
            </div>
            <Footer />
        </div>
    );
}
export default SearcherSelector;