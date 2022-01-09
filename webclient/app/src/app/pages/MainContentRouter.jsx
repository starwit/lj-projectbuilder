import React from "react";
import {Route} from "react-router-dom";

import Home from "./home/Home";
import AppOverview from "./appOverview/AppOverview";
import AppEditor from "./appEditor/AppEditor";
import AppTemplateOverview from "./appTemplate/AppTemplateOverview";

function MainContentRouter() {
    return (
        <>
            <Route path={"/app/:appId/edit"} component={AppEditor}/>
            <Route exact path={"/app/:appId"} component={AppOverview}/>
            <Route exact path={"/"} component={Home}/>
            <Route path={"/apptemplate"} component={AppTemplateOverview}/>
            <Route path='/logout' component={() => {                
                window.location.href = window.location.pathname + "api/usermanagement/logout"; 
                return null;
            }}/>
        </>
    )
}

export default MainContentRouter;