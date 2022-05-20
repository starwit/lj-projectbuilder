import React from "react";
import {Fab} from "@mui/material";
import {Add} from "@mui/icons-material";
import AddFabButtonStyles from "./AddFabButtonStyles";
import PropTypes from "prop-types";

function AddFabButton(props) {
    const addFabButton = AddFabButtonStyles();
    const {onClick} = props;

    return (
        <div className={addFabButton.fabWrapper}>
            <Fab aria-label="add" onClick={onClick}>
                <Add/>
            </Fab>
        </div>
    );
}

AddFabButton.propTypes = {
    onClick: PropTypes.func.isRequired
};

export default AddFabButton;
