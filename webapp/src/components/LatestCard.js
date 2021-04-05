import { useSubscription, gql } from '@apollo/client';
import Card from "./Card"

const NEW_CARDS_SUBSCRIPTION = gql`
  subscription {
    latestCard {
      id
      title
      description
    }
  }
`;

const LatestCardList = () => {
  const {
    data,
    loading,
  } = useSubscription(NEW_CARDS_SUBSCRIPTION);

  return (
    <div>
      {!loading && (
        <>
          <Card key={data.latestCard.id} card={data.latestCard} />
        </>
      )}
    </div>
  );
};

export default LatestCardList;
