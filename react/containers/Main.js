import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import Layout from './Layout';

import store from '../store';

import 'semantic-ui-css/semantic.min.css';
import {Provider} from 'react-redux';

export default class Main extends React.Component {

    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <Layout />
                </BrowserRouter>
            </Provider>
        );
    }

}