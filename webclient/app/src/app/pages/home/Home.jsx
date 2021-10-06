import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import ProjectCard from "../../commons/projectCard/ProjectCard";
import AddCard from "../../commons/addCard/AddCard";
import {useHistory} from "react-router-dom";

const projects= [
    {
        id: 1,
        name: "test",
        description: "Das ist ein Test"
    }
];

function Home() {
    const history = useHistory();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                Deine Apps
            </Typography>
            <Grid container spacing={5}>
                {projects.map(project => (
                    <Grid item sm={4}>
                        <ProjectCard onEditClick={() => {history.push("/project/"+project.id+"/edit")}} onDeleteClick={() => {}} project={project} />
                    </Grid>
                ))}

                <Grid item sm={4}>
                    <AddCard onClick={() => {}}/>
                </Grid>
            </Grid>
        </Container>
    )

}

export default Home;