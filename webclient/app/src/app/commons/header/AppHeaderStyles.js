import {makeStyles} from "@mui/styles"

const drawerWidth = 240;
const appBarHeight = "5rem";

const AppHeaderStyles = makeStyles(theme => ({
    root: {
        display: "flex",
    },
    appBar: {
        zIndex: theme.zIndex.modal + 1,
        backgroundColor: theme.palette.primary.main,
        height: "5rem"
    },
    drawer: {
        [theme.breakpoints.up("sm")]: {
            width: drawerWidth,
            flexShrink: 0
        }
    },
    menuTitle: {
        display: "flex",
        justifyContent: "left",
        paddingLeft: 20,
        [theme.breakpoints.up("sm")]: {
            display: "flex",
            width: "100%",
            justifyContent: "center",
        }
    },
    menuLogo: {
        display: "none",
        [theme.breakpoints.up("sm")]: {
            display: "flex",
            width: 350,
        }
    },
    menuLogoResponsive: {
        display: "flex",
        width: 50,
        [theme.breakpoints.up("sm")]: {
            display: "none",
        }
    },
    menuLogoImg: {
        height: "100%",
        width: "auto"
    },
    spacer: {
        flexGrow: 1
    },
    menuButton: {
        marginRight: 20,
        [theme.breakpoints.up("sm")]: {
            display: "none"
        }
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3
    },
    toolbar: {
        ...theme.mixins.toolbar,
        backgroundColor: theme.palette.primary.main,
    },
    linkButton: {
        marginRight: theme.spacing(3),
        marginLeft:theme.spacing(3),
    },
    contentSpacer: {
        height: appBarHeight
    }
}))
export default AppHeaderStyles;