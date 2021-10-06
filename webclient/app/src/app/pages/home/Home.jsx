import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import ProjectCard from "../../commons/projectCard/ProjectCard";

function Home() {

    return (
        <Container>
            <Typography variant={"h2"}>
                Deine Projekte
            </Typography>
            <Grid container>
                <Grid item sm={4}>
                    <ProjectCard name={"test"} description={"testDescription"} />
                </Grid>
            </Grid>
        </Container>
    )

}

export default Home;