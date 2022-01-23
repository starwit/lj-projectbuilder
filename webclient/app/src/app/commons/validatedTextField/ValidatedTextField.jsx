import React, {useCallback, useEffect} from "react";
import {TextField} from "@mui/material";
import PropTypes from "prop-types";

function ValidatedTextField(props) {

    const {value, regex, overwriteError, handleHasError} = props;

    const isError = useCallback(() => {
        return overwriteError || !regex.test(value);
    }, [overwriteError, value, regex])

    useEffect(() => {
        if (handleHasError) {
            handleHasError(isError());
        }
    }, [value, handleHasError, isError])


    return (
        <TextField
            error={isError()}
            value={value}
            {...props}
        />
    )

}

ValidatedTextField.defaultProps = {
    value: "",
}

ValidatedTextField.propTypes = {
    helperText: PropTypes.string.isRequired,
    regex: PropTypes.any.isRequired,
    onChange: PropTypes.func.isRequired,
    value: PropTypes.string,
    overwriteError: PropTypes.bool,
    handleHasError: PropTypes.func
}

export default ValidatedTextField;