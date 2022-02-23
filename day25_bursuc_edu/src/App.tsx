import './App.css';
import Menu from "./Menu/Menu";
import ShowingBox from "./ShowingBox/ShowingBox";
import "./reducers/inputFields";
import React from 'react';

function App() {
    console.log("App rerender");
  return (
      <div>
          <div className="bodyTag">
              <Menu />
              <ShowingBox />
          </div>
      </div>
  );
}

export default App;
