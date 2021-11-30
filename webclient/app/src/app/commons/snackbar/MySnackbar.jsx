import React, {Component} from "react";
import Snackbar from "@material-ui/core/Snackbar";
// Icons
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";

class MySnackbar extends Component {
    //snackbar attributes
    message = "";
    orientation = {
        vertical: "bottom",
        horizontal: "right"
    };

    state = {open: false};

    handleClose = (event, reason) => {
        if (reason === "clickaway") {
            return;
        }
        this.setState({open: false});
    };

    showSnackbar = message => {
        this.message = message;
        this.setState({open: true});
    };

    render() {
        return (
            <Snackbar
                anchorOrigin={this.orientation}
                open={this.state.open}
                autoHideDuration={6000}
                onClose={this.handleClose}
                message={<span id="message-id">{this.message}</span>}
                action={[
                    <IconButton
                        key="close"
                        aria-label="Close"
                        color="inherit"
                        onClick={this.handleClose}
                    >
                        <CloseIcon/>
                    </IconButton>
                ]}
            />
        );
    }
}

export default MySnackbar;
