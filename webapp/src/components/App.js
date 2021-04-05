import React from "react";
import CardList from "./CardList";
import CreateCard from "./CreateCard";
import LatestCardList from "./LatestCard";
import { Switch, Route } from "react-router-dom";
import Header from "./Header";
import Login from "./Login";

const App = () => {
  return (
    <div className="center w85">
      <Header />
      <div className="ph3 pv1 background-gray">
        <Switch>
          <Route exact path="/" component={CardList} />
          <Route exact path="/new" component={LatestCardList} />
          <Route
            exact
            path="/create"
            component={CreateCard}
          />
          <Route exact path="/login" component={Login} />
        </Switch>
      </div>
    </div>
  );
};

export default App;
