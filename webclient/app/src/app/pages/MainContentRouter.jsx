import React from "react";
import {Route} from "react-router-dom";
import Home from "./home/Home";
import ProjectOverview from "./projectOverview/ProjectOverview";
import ProjectEditor from "./projectEditor/ProjectEditor";

function MainContentRouter() {
    return (
        <>
            <Route path={"/project/:projectId/edit"} component={ProjectEditor}/>
            <Route path={"/project/:projectId"} component={ProjectOverview}/>
            <Route exact path={"/"} component={Home}/>
        </>
    )
}

export default MainContentRouter;