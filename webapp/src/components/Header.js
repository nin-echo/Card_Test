import React from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { AUTH_TOKEN } from "../constant";

const Header = () => {
  const history = useHistory();
  const authToken = localStorage.getItem(AUTH_TOKEN);
  return (
    <div className="flex pa1 justify-between nowrap blue">
      <div className="flex flex-fixed white">
        <div className="fw7 mr1">My Cards</div>
        <Link to="/" className="ml1 no-underline white">
          All
        </Link>
        <div className="ml1">|</div>
        <Link to="/new" className="ml1 no-underline white">
          Latest
        </Link>
        <div className="ml1">|</div>
        <Link to="/search" className="ml1 no-underline white">
          Search
        </Link>
        <div className="flex">
          <div className="ml1">|</div>
          <Link to="/create" className="ml1 no-underline white">
            Submit
          </Link>
        </div>
      </div>
      <div className="flex flex-fixed">
        {authToken ? (
          <div
            className="ml1 pointer black"
            onClick={() => {
              localStorage.removeItem(AUTH_TOKEN);
              history.push(`/`);
            }}
          >
            logout
          </div>
        ) : (
          <Link to="/login" className="ml1 no-underline white">
            login
          </Link>
        )}
      </div>
    </div>
  );
};

export default Header;
