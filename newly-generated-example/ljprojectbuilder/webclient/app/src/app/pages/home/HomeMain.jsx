import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import HomeAll from "./all/HomeAll";

class HomeMain extends Component {

  render() {

    return (
        <React.Fragment>
          <Route exact path="/" component={HomeAll}/>
        </React.Fragment>
    );
  }
}

export default HomeMain;
