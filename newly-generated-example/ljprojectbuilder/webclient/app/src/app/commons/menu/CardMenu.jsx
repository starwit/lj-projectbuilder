import React, {Component} from "react";
import {withRouter} from "react-router-dom";
// Material UI Components
import IconButton from "@material-ui/core/IconButton";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import MoreVertIcon from "@material-ui/icons/MoreVert";

const options = ["Open", "Edit", "Delete", "Toggle Stream"];

const ITEM_HEIGHT = 48;

class CardMenu extends Component {
    state = {
        anchorEl: null
    };

    handleClick = event => {
        this.setState({anchorEl: event.currentTarget});
    };

    // trigger diffrent functions after selecting
    handleClose = (index, option) => {
        this.setState({anchorEl: null}, function () {
            if (option === "Delete") {
                this.onDelete(index);
            } else if (option === "Open") {
                this.onOpen(index);
            } else if (option === "Edit") {
                this.onEdit(index);
            } else if (option === "Toggle Stream") {
                this.props.switchImageSource();
            }
        });
    };

    onDelete = index => {
        this.props.deleteCamera(index);
    };

    onOpen = index => {
        this.props.markSelected(index);
        this.props.history.push("/detail/" + index);
    };

    onEdit = index => {
        this.props.markSelected(index);
        this.props.history.push("/edit/" + index);
    };

    render() {
        const {anchorEl} = this.state;
        const open = Boolean(anchorEl);

        return (
            <div>
                <IconButton
                    aria-label="More"
                    aria-owns={open ? "long-menu" : undefined}
                    aria-haspopup="true"
                    onClick={this.handleClick}
                >
                    <MoreVertIcon/>
                </IconButton>
                <Menu
                    id="long-menu"
                    anchorEl={anchorEl}
                    open={open}
                    onClose={() => this.handleClose()}
                    PaperProps={{
                        style: {
                            maxHeight: ITEM_HEIGHT * 5,
                            width: 200
                        }
                    }}
                >
                    {options.map(option => (
                        <MenuItem
                            key={option}
                            selected={option === "Open"}
                            onClick={() => this.handleClose(this.props.selectedCard, option)}
                        >
                            {option}
                        </MenuItem>
                    ))}
                </Menu>
            </div>
        );
    }
}

export default withRouter(CardMenu);
