import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import AppCard from "../../commons/appCard/AppCard";
import AddCard from "../../commons/addCard/AddCard";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";

const apps = [
    {
        id: 1,
        name: "test",
        description: "Das ist ein Test"
    }
];

function Home() {
    const history = useHistory();
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("home.yourApps")}
            </Typography>
            <Grid container spacing={5}>
                {apps.map(app => (
                    <Grid item sm={4}>
                        <AppCard
                            onEditClick={() => {
                                history.push("/app/" + app.id + "/edit")
                            }}
                            onDeleteClick={() => {
                            }}
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