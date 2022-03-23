import React from "react";
import { Route } from "react-router-dom";
import Home from "./apps/AppOverview";
import AppOverview from "./apps/AppSummary";
import AppEditor from "./apps/AppEditor";
import AppTemplateOverview from "./appTemplates/AppTemplateOverview";

function MainContentRouter() {
    return (
        <>
            <Route path={"/apps/:appId/edit"} component={AppEditor} />
            <Route exact path={"/apps/:appId"} component={AppOverview} />
            <Route exact path={"/"} component={Home} />
            <Route path={"/apptemplates/"} component={AppTemplateOverview} />
            <Route
                path="/logout"
                component={() => {
                    window.location.href =
                        window.location.pathname + "api/user/logout";
                    return null;
                }}
            />
        </>
    );
}

export default MainContentRouter;
