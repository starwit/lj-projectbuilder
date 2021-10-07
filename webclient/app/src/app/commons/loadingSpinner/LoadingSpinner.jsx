import React, {useEffect, useState} from "react";
import {CircularProgress, Fade, Typography} from "@mui/material";
import PropTypes from "prop-types";

function LoadingSpinner(props) {
    const {message} = props;
    const [showMessage, setShowMessage] = useState(false);

    useEffect(() => {
        setTimeout(function () {
            setShowMessage(true);
        }, 1000);
    })

    return (
        <div style={{height: "100%", display: "flex", justifyContent: "center", alignContent: "center"}}>
            <div style={{display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center"}}>
                <CircularProgress color={"secondary"} style={{marginBottom: "2rem"}}/>
                <Fade in={showMessage}>
                    <Typography variant={"body1"} color={"text.secondary"}>{message}</Typography>
                </Fade>
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