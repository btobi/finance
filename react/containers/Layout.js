import React from "react";
import {Route, Switch} from "react-router-dom";
import * as MobileDetect from "mobile-detect";

export default class Layout extends React.Component {

    render() {

        const isMobile = new MobileDetect(window.navigator.userAgent).mobile();

        const mobileStyle = !isMobile ? {marginLeft: "10rem"} : {};

        let profileStyle = {};

        switch(window.applicationProfile) {
            case "local":
                profileStyle = {borderTop: "3px solid red"};
                break;
            case "develop":
                profileStyle = {borderTop: "3px solid green"}
        }

        return (
            <div style={profileStyle}>
                <div style={mobileStyle}>
                    <div style={{padding: "1rem"}}>
                        <Switch>
                            {/*<Route path='/home' component={Home}/>*/}
                            <Route render={() => <h1>Not found</h1>}/>
                        </Switch>
                    </div>
                </div>
            </div>
        );
    }

}