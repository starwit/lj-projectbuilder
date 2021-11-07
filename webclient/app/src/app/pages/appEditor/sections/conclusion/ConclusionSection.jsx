import React from "react";
import {Button, Container, Grid, List, ListItem, ListItemText, Typography} from "@mui/material";
import {Download} from "@mui/icons-material";

function ConclusionSection() {

    return (
        <Container>
            <Grid container spacing={5}>
                <Grid item md={9}>
                    <Typography variant={"h5"} gutterBottom>Allgemein</Typography>
                    <List sx={{width: '100%'}}>
                        <ListItem>
                            <ListItemText primary="Photos" secondary="Name"/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary="Photos" secondary="Template"/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary="Photos" secondary="Package Name"/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid
                    item
                    md={3}
                    style={{display: "flex", justifyContent: "center", alignItems: "center", width: "100%"}}
                >
                    <Button startIcon={<Download/>} color={"primary"} variant={"contained"}
                            size={"large"}>Download</Button>
                </Grid>

            </Grid>
            <Typography variant={"h5"} gutterBottom>ER-Diagramm</Typography>

        </Container>
    )

}

export default ConclusionSection