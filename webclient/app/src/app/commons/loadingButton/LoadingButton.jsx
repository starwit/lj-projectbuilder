import React from "react";
import {Button, CircularProgress} from "@mui/material";
import LoadingButtonStyles from "./LoadingButtonStyles";

function LoadingButton(props) {

    const {loading, children, disabled} = props;
    const loadingButtonStyles = LoadingButtonStyles();

    return (
        <>
            <Button
                disabled={loading || disabled}
                {...props}
            >
                {children}
            </Button>
            {
                loading && (
                    <CircularProgress
                        size={24}
                        className={loadingButtonStyles.button}
                    />
                )
            }
        </>
    )

}

export default LoadingButton;