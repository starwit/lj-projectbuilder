import React, { useEffect, useState } from "react";
import {TextField} from "@mui/material";
import PropTypes from "prop-types";

function SimpleValidatedTextField(props) {

    const {value, errortext, regex, iscreate} = props;
    const [error, setError] = useState(false);
    const [initial, setInitial] = useState(true);

    useEffect(() => {
        setError(!regex.test(value));
    }, [value, regex]);

    return (
        <TextField
            error={!(iscreate && initial) && error}
            value={value}
            onBlur={() => setInitial(false)}
            helperText={!(iscreate && initial) && error ? errortext : ""}
            {...props}
        />
    )

}

SimpleValidatedTextField.propTypes = {
    errortext: PropTypes.string.isRequired,
    regex: PropTypes.any.isRequired,
    value: PropTypes.string,
    iscreate: PropTypes.number
}

export default SimpleValidatedTextField;