import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import AppCard from "../../commons/appCard/AppCard";
import AddCard from "../../commons/addCard/AddCard";
import {useHistory} from "react-router-dom";

const apps = [
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
                {apps.map(app => (
                    <Grid item sm={4}>
                        <AppCard
                            onEditClick={() => {
                                history.push("/app/" + app.id + "/edit")
                            }}
                            onDeleteClick={() => {}}
                            app={app}/>
                    </Grid>
                ))}

                <Grid item sm={4}>
                    <AddCard
                        onClick={() => {
                        }}
                    />
                </Grid>
            </Grid>
        </Container>
    )

}

export default Home;