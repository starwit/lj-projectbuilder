import React from 'react';
import ReactDOM from 'react-dom';
import DeviceAll from './App';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<DeviceAll />, div);
  ReactDOM.unmountComponentAtNode(div);
});
