import {AppContainer} from "react-hot-loader";
import ReactDOM from "react-dom";
import Main from "./containers/Main";
import React from "react";

const rootEl = document.getElementById("root");
const render = Component =>
  ReactDOM.render(
    <AppContainer>
      <Component />
    </AppContainer>,
    rootEl
  );

render(Main);
if (module.hot) module.hot.accept("./containers/Main", () => render(Main));
