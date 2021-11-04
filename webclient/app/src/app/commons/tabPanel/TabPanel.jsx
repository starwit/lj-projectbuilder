import {Box, Typography} from "@mui/material";
import React from "react";
import TabPanelStyles from "./TabPanelStyles";

function TabPanel(props) {
    const {children, value, index, ...other} = props;
    const tabPanel = TabPanelStyles();

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box className={tabPanel.box}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}
export default TabPanel;