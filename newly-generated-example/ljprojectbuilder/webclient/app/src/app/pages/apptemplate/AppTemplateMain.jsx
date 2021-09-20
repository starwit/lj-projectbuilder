import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import AppTemplateAll from "./all/AppTemplateAll";
import AppTemplateSingle from "./single/AppTemplateSingle";

class AppTemplateMain extends Component {

  render() {

    return (
        <React.Fragment>
          <Route exact path="/apptemplate" component={AppTemplateAll}/>
          <Route exact path="/apptemplate/create" component={AppTemplateSingle}/>
          <Route exact path="/apptemplate/update/:id" component={AppTemplateSingle}/>
        </React.Fragment>
    );
  }
}

export default AppTemplateMain;
