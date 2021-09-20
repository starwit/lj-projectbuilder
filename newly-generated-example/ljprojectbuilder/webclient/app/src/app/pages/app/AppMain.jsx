import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import AppAll from "./all/AppAll";
import AppSingle from "./single/AppSingle";

class AppMain extends Component {

  render() {

    return (
        <React.Fragment>
          <Route exact path="/app" component={AppAll}/>
          <Route exact path="/app/create" component={AppSingle}/>
          <Route exact path="/app/update/:id" component={AppSingle}/>
        </React.Fragment>
    );
  }
}

export default AppMain;
