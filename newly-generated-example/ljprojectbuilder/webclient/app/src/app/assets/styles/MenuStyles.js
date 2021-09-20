const drawerWidth = 240;

const MenuStyles = theme => (
    {
    root: {
        display: "flex",
    },
    appBar: {
        zIndex: theme.zIndex.modal + 1,
        backgroundColor: theme.palette.primary.main,
    },
    drawer: {
        [theme.breakpoints.up("sm")]: {
            width: drawerWidth,
            flexShrink: 0
        }
    },
    listItem: {
        '&:hover': {
            backgroundColor: theme.palette.secondary.main + " !important",
            color: "#ffffff !important"
        }
    },
    listEntry: {
        color: "#ffffff",
        '&:hover': {
            color: "#ffffff !important"
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
        height: 40,
    },
    menuButton: {
        marginRight: 20,
        [theme.breakpoints.up("sm")]: {
            display: "none"
        }
    },
    drawerPaper: {
        width: drawerWidth
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3
    },
    toolbar: theme.mixins.toolbar,
    version: {
        position: "fixed",
        bottom: 0,
        mb: "20px",
        fontSize: 10,
        '& a': {
            color: "white"
        }
    }
});

export default MenuStyles;
