import React from "react";
import {Card, CardActionArea, Typography} from "@mui/material";
import {Add} from "@mui/icons-material";
import PropTypes from "prop-types";
import AddCardStyles from "./AddCardStyles";

function AddCard(props) {

    const {onClick} = props;
    const addCardStyles = AddCardStyles();

    return (
        <Card elevation={5}>
            <CardActionArea onClick={onClick}>
                <div className={addCardStyles.actionArea}>
                    <Add className={addCardStyles.addIcon}/>
                    <Typography variant={"body1"}>Hinzufügen</Typography>
                </div>
            </CardActionArea>
        </Card>
    )

}

AddCard.defaultProps = {
    onClick: PropTypes.func.isRequired
}

export default AddCard;