import React, {useEffect, useState} from "react";
import {CircularProgress, Fade, Typography} from "@mui/material";
import PropTypes from "prop-types";
import LoadingSpinnerStyles from "./LoadingSpinnerStyles";

function LoadingSpinner(props) {
    const {message} = props;
    const [showMessage, setShowMessage] = useState(false);
    const loadingSpinnerStyles = LoadingSpinnerStyles();

    useEffect(() => {
        const messageTimer = setTimeout(function() {
            setShowMessage(true);
        }, 1000);

        return () => {
            clearTimeout(messageTimer);
        };
    });

    return (
        <div className={loadingSpinnerStyles.root}>
            <div className={loadingSpinnerStyles.contentWrapper}>
                <CircularProgress color={"secondary"} className={loadingSpinnerStyles.loadingSpinner}/>
                <Fade in={showMessage}>
                    <Typography variant={"body1"} className={loadingSpinnerStyles.message}>{message}</Typography>
                </Fade>
            </div>
        </div>
    );
}

LoadingSpinner.defaultProps = {
    message: "Loading..."
};

LoadingSpinner.propTypes = {
    message: PropTypes.string
};
export default LoadingSpinner;
