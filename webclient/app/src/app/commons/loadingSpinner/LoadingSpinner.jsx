import React from "react";
import {CircularProgress, Typography} from "@mui/material";
import PropTypes from "prop-types";

function LoadingSpinner(props) {
    const {message} = props;
    return (
        <div style={{height: "100%", display: "flex", justifyContent: "center", alignContent: "center"}}>
            <div style={{display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center"}}>
                <CircularProgress color={"secondary"} style={{marginBottom: "2rem"}}/>
                <Typography variant={"body1"} color={"text.secondary"}>{message}</Typography>
            </div>
        </div>
    )
}

LoadingSpinner.defaultProps = {
    message: "Loading..."
}

LoadingSpinner.propTypes = {
    message: PropTypes.string
}
export default LoadingSpinner;