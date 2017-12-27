import axios from 'axios'

const API_PATH = '/api';

axios.defaults.headers = {
    ...axios.defaults.headers,
};

export default class Http {

    static get(path) {
        console.log('GET DATA from ' + path);
        return axios.get(getPath(path));
    }

    static post(path, data = {}) {
        console.log('POSTING DATA');
        console.log(data);
        return axios.post(getPath(path), data);
    }

    static delete(path, data = {}) {
        console.log('DELETE DATA');
        console.log(data);
        return axios.post(getPath(path), data, {
            headers: { 'X-METHODOVERRIDE': 'DELETE' }
        })
    }

}

function getPath(path = '') {
    return API_PATH + path;
}
