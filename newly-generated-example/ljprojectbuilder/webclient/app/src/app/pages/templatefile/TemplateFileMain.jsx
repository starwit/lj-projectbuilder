import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import TemplateFileAll from "./all/TemplateFileAll";
import TemplateFileSingle from "./single/TemplateFileSingle";

class TemplateFileMain extends Component {

  render() {

    return (
        <React.Fragment>
          <Route exact path="/templatefile" component={TemplateFileAll}/>
          <Route exact path="/templatefile/create" component={TemplateFileSingle}/>
          <Route exact path="/templatefile/update/:id" component={TemplateFileSingle}/>
        </React.Fragment>
    );
  }
}

export default TemplateFileMain;
