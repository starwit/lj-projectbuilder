import React from "react";
import {Card, CardActionArea} from "@mui/material";
import {Add} from "@mui/icons-material";
import PropTypes from "prop-types";

function AddCard(props) {
    const {onClick} = props;

    return (
        <Card elevation={5}>
            <CardActionArea style={{padding: "2rem", display: "flex", justifyContent: "center"}} onClick={onClick}>
                <Add style={{fontSize: "5rem"}} color={"text.secondary"} />
            </CardActionArea>
        </Card>
    )

}

AddCard.defaultProps = {
    onClick: PropTypes.func.isRequired
}

export default AddCard;