import React from "react";
import {Button, Container, Grid, List, ListItem, ListItemText, Skeleton, Typography} from "@mui/material";
import {Download} from "@mui/icons-material";
import ErSection from "../erSection/ErSection";
import PropTypes from "prop-types";
import ConclusionSectionStyles from "./ConclusionSectionStyles";

function ConclusionSection(props) {

    const {entities, appName, packageName, templateName} = props;
    const conclusionSectionStyles = ConclusionSectionStyles();

    function renderLoadingText(text) {
        if (!text) {
            return <Skeleton animation={"wave"}/>
        }
        return <Typography
            variant={"h6"}
            className={conclusionSectionStyles.listItemText}
        >
            {text}
        </Typography>;
    }

    return (
        <Container className={conclusionSectionStyles.root}>
            <Grid container spacing={5}>
                <Grid item md={9}>
                    <List className={conclusionSectionStyles.list}>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(appName)} secondary="Name"/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(templateName)} secondary="Template"/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(packageName)} secondary="Package Name"/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid
                    item
                    md={3}
                    className={conclusionSectionStyles.downloadGrid}
                >
                    <Button
                        startIcon={<Download/>}
                        color={"primary"}
                        variant={"contained"}
                        size={"large"}
                    >
                        Download
                    </Button>
                </Grid>

            </Grid>
            <Typography variant={"h4"} gutterBottom>ER-Diagramm</Typography>
            <ErSection entities={entities} dense editable={false}/>
        </Container>
    )
}

ConclusionSection.propTypes = {
    entities: PropTypes.array,
    appName: PropTypes.string,
    packageName: PropTypes.string,
    templateName: PropTypes.string
}

ConclusionSection.defaultProps = {
    appName: <Skeleton animation={"wave"}/>,
    templateName: <Skeleton animation={"wave"}/>,
    packageName: <Skeleton animation={"wave"}/>,
}

export default ConclusionSection
