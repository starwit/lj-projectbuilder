import React from "react";
import {Card, CardActionArea, Typography} from "@mui/material";
import {Add} from "@mui/icons-material";
import PropTypes from "prop-types";
import AddCardStyles from "./AddCardStyles";
import {useTranslation} from "react-i18next";

function AddCard(props) {
    const {onClick} = props;
    const addCardStyles = AddCardStyles();
    const {t} = useTranslation();

    return (
        <Card elevation={5}>
            <CardActionArea onClick={onClick}>
                <div className={addCardStyles.actionArea}>
                    <Add className={addCardStyles.addIcon}/>
                    <Typography variant={"body1"}>{t("button.create")}</Typography>
                </div>
            </CardActionArea>
        </Card>
    );
}

AddCard.defaultProps = {
    onClick: PropTypes.func.isRequired
};

export default AddCard;
