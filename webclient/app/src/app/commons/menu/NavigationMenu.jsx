import React, {Component} from "react";
// Material UI Components
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import Hidden from "@material-ui/core/Hidden";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Button from "@material-ui/core/Button";
import {withStyles} from '@material-ui/core/styles';
import MenuStyles from "../../assets/styles/MenuStyles";

class NavigationMenu extends Component {
    addButtons = (navListSection, sectionIndex) => {
        return (
            navListSection.elements.map(element => {
                return (
                    <ListItem key={sectionIndex + "-" + element.id}>
                        <Button
                            component={element.component}
                            to={element.to}
                            variant="contained"
                            color="primary">
                            {element.title}
                        </Button>
                    </ListItem>);
            })
        );
    };

    addLinks = (navListSection, sectionIndex) => {
        return (
            navListSection.elements.map(element => {
                return (
                    <ListItem key={sectionIndex + "-" + element.id}
                              button className={this.props.classes.listItem}
                              component={element.component}
                              to={element.to}
                              selected={this.props.selected === element.to}>
                        <ListItemText className={this.props.classes.listEntry} primary={element.title}/>
                    </ListItem>
                );
            })
        );
    };

    render() {
        const {classes, theme, buildVersion} = this.props;
        const drawer = (
            <div>
                <div className={classes.toolbar}/>
                <Divider/>
                <List>
                    {this.props.navListSections.map((navListSection, index) => {
                        switch (navListSection.type) {
                            case "Button":
                                return this.addButtons(navListSection, index);
                            case "Divider":
                                return (<Divider key={index}/>);
                            case "Link":
                            default:
                                return this.addLinks(navListSection, index);
                        }
                    })}
                    <ListItem className={classes.version}>
                        <a href={window.location.pathname + "info.txt"} target="_blank" rel="noopener noreferrer">
                            {buildVersion}
                        </a>
                    </ListItem>
                </List>
            </div>
        );

        return (
            <nav className={classes.drawer}>
                <Hidden smUp implementation="css">
                    <Drawer
                        container={this.props.container}
                        variant="persistent"
                        anchor={theme.direction === "rtl" ? "right" : "left"}
                        open={this.props.mobileOpen}
                        onClose={this.handleDrawerToggle}
                        classes={{
                            paper: classes.drawerPaper
                        }}
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
                <Hidden xsDown implementation="css">
                    <Drawer
                        classes={{
                            paper: classes.drawerPaper
                        }}
                        variant="permanent"
                        open>
                        {drawer}
                    </Drawer>
                </Hidden>
            </nav>
        );
    }
}

export default withStyles(MenuStyles, {withTheme: true})(NavigationMenu);
