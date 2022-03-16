import React, {useEffect} from "react";
import AppHeader from "./commons/appHeader/AppHeader";
import MainContentRouter from "./pages/MainContentRouter";
import {CssBaseline} from "@mui/material";
import AppStyles from "./AppStyles";
import {useSnackbar} from "notistack";
import axios from "axios";
import {useTranslation} from "react-i18next";


function App() {

    const appStyles = AppStyles();
    const {t} = useTranslation();


    const {enqueueSnackbar} = useSnackbar();

    useEffect(() => {
        if (axios.interceptors.response.handlers.length > 0) {
            return;
        }

        axios.interceptors.response.use(function (response) {
            // Any status code that lie within the range of 2xx cause this function to trigger
            // Do something with response data
            return response;
        }, function (error) {
            let errorMessage = "error.unknown";

            if (error?.request) {
                if (navigator.onLine) {
                    errorMessage = "error.serverOffline";
                    console.error("Server cannot be reached.");
                } else {
                    errorMessage = "error.userOffline";
                    console.log("User seems to be offline. Cannot complete request.");
                }
            }
            if (error?.response) {
                const {config, data, status} = error.response;
                switch (config.method) {
                    case "get":
                        errorMessage = "error.general.get";
                        break;
                    case "delete":
                        errorMessage = "error.general.delete";
                        break;
                    case "post":
                        errorMessage = "error.general.create";
                        break;
                    case "put":
                        errorMessage = "error.general.update";
                        break;
                    default:
                        errorMessage = "error.unknown";
                }

                if (data.locKey) {
                    errorMessage = data.locKey;
                }

                console.error(`A ${config.method} request failed with status code ${status}:`, data, config)
            }

            enqueueSnackbar(t(errorMessage), {variant: "error"})

            return Promise.reject(error);
        });
    }, [enqueueSnackbar, t]);


    return (
        <React.Fragment>
            <div className={appStyles.background}>
                <CssBaseline/>
                <AppHeader/>
                <MainContentRouter/>
            </div>
        </React.Fragment>
    );

}

export default App;