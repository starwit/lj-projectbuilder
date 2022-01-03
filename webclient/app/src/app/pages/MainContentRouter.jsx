import React from "react";
import {Route} from "react-router-dom";

import Welcome from "./default/Welcome";
import Error403 from "./default/Error403";
import Error500 from "./default/Error500";

import Home from "./home/Home";
import AppOverview from "./appOverview/AppOverview";
import AppEditor from "./appEditor/AppEditor";
import AppTemplateOverview from "./appTemplate/AppTemplateOverview";

function MainContentRouter() {
    return (
        <>
            <Route exact path={"/default/welcome"} component={Welcome}/>
            <Route exact path={"/default/403"} component={Error403}/>
            <Route exact path={"/default/500"} component={Error500}/>
            <Route path={"/app/:appId/edit"} component={AppEditor}/>
            <Route exact path={"/app/:appId"} component={AppOverview}/>
            <Route exact path={"/"} component={Home}/>
            <Route path={"/apptemplate"} component={AppTemplateOverview}/>
        </>
    )
}

export default MainContentRouter;