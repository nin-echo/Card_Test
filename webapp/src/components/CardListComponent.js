import React, { Component } from "react";
import Card from "./Card";

class CardListComponent extends Component {
  componentDidMount() {
    this.props.subscribeToMoreCards();
  }

  render() {
    const { data, loading, error } = this.props;
    return (
      <div>
        {loading && <p>Loading...</p>}
        {error && <pre>{JSON.stringify(error, null, 2)}</pre>}
        {data && (
          <>
            {data.cards.map((card) => (
              <Card key={card.id} card={card} />
            ))}
          </>
        )}
      </div>
    );
  }
}

export default CardListComponent;
