import React from "react";
import CardListComponent from "./CardListComponent";
import { useQuery, gql } from "@apollo/client";

const CARDS_QUERY = gql`
  query Cards($account_id: ID!) {
    cards(account_id: $account_id) {
      id
      title
      description
    }
  }
`;

const NEW_CARDS_SUBSCRIPTION = gql`
  subscription {
    latestCard {
      id
      title
      description
    }
  }
`;

const CardList = () => {
  const { data, loading, error, subscribeToMore } = useQuery(CARDS_QUERY, {
    variables: {
      account_id: "1",
    },
  });

  return (
    <CardListComponent
      data={data}
      loading={loading}
      error={error}
      subscribeToMoreCards={() =>
        subscribeToMore({
          document: NEW_CARDS_SUBSCRIPTION,
          updateQuery: (prev, { subscriptionData }) => {
            if (!subscriptionData.data) return prev;
            const newCard = subscriptionData.data.latestCard;
            const exists = prev.cards.find(({ id }) => id === newCard.id);
            if (exists) return prev;

            return Object.assign({}, prev, {
              cards: [newCard, ...prev.cards],
            });
          },
        })
      }
    />
  );
};

export default CardList;
