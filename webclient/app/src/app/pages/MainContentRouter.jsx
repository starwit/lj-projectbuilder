import React from "react";
import {Route} from "react-router-dom";
import Home from "./home/Home";
import AppOverview from "./appOverview/AppOverview";
import AppEditor from "./appEditor/AppEditor";

function MainContentRouter() {
    return (
        <>
            <Route path={"/app/:appId/edit"} component={AppEditor}/>
            <Route exact path={"/app/:appId"} component={AppOverview}/>
            <Route exact path={"/"} component={Home}/>
        </>
    )
}

export default MainContentRouter;