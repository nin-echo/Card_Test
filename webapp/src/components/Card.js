import React from 'react';

const Card = (props) => {
  const { card } = props;
  return (
    <div>
      <div>
        {card.title} ({card.description})
      </div>
    </div>
  );
};

export default Card;