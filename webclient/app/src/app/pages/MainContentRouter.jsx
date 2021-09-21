import React from "react";
import {Route} from "react-router-dom";
import Home from "./home/Home";

function MainContentRouter(){
    return(
        <>
        <Route path={"/"} component={Home}/>
        </>
    )
}
export default MainContentRouter;