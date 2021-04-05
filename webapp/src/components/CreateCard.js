import React, { useState } from "react";
import { useMutation, gql } from "@apollo/client";
// import { useHistory } from "react-router";

const CREAT_CARD_MUTATION = gql`
  mutation CreateCard($account_id: ID!, $title: String!, $description: String!) {
    createCard(account_id: $account_id, title: $title, description: $description) {
      title
      description
    }
  }
`;

const CreateCard = () => {
  // const history = useHistory();

  const [formState, setFormState] = useState({
    title: "",
    description: "",
  });

  const [createCard] = useMutation(CREAT_CARD_MUTATION, {
    variables: {
      account_id:"1",
      title: formState.title,
      description: formState.description,
    },
    // onCompleted: () => history.push("/"),
  });

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          createCard();
        }}
      >
        <div className="flex flex-column mt3">
          <input
            className="mb2"
            value={formState.title}
            onChange={(e) => {
              setFormState({
                ...formState,
                title: e.target.value,
              });
            }}
            type="text"
            placeholder="A title for the card"
          ></input>
          <input
            className="mb2"
            value={formState.description}
            onChange={(e) => {
              setFormState({
                ...formState,
                description: e.target.value,
              });
            }}
            type="text"
            placeholder="A description for the card"
          ></input>
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default CreateCard;
