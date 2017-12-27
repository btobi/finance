var path = require("path");
var webpack = require("webpack");
var BundleTracker = require("webpack-bundle-tracker");

module.exports = {
    entry: [
        "react-hot-loader/patch",
        // activate HMR for React

        "webpack-dev-server/client?http://localhost:3000",
        // bundle the client for webpack-dev-server
        // and connect to the provided endpoint

        "webpack/hot/only-dev-server",
        // bundle the client for hot reloading
        // only- means to only hot reload for successful updates

        "./react/index.js",
        // the entry point of our app
    ],

    output: {
        filename: "bundle.js",
        // the output bundle

        path: path.resolve(__dirname, "dist"),

        publicPath: "http://localhost:3000/assets/bundles/"

        // necessary for HMR to know where to load the hot update chunks
    },

    resolve: {
        modules: [
            path.resolve("./react"),
            path.resolve("./node_modules"),
        ]
    },

    devtool: "inline-source-map",

    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: "babel-loader",
                query: {
                    presets: ["babel-preset-react", "babel-preset-stage-2"].map(require.resolve),
                    plugins: ["babel-plugin-react-html-attrs", "babel-plugin-transform-decorators-legacy", "babel-plugin-transform-class-properties", "babel-plugin-lodash"].map(require.resolve),
                }
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
            {
                test: /\.(eot|svg|ttf|woff|woff2)(\?\S*)?$/,
                loader: "file-loader"
            },
            {
                test: /\.(png|jpg|gif|svg)$/,
                loader: "file-loader",
                options: {
                    name: "[name].[ext]?[hash]"
                }
            }
        ],
    },

    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        // enable HMR globally

        new webpack.NamedModulesPlugin(),
        // prints more readable module names in the browser console on HMR updates

        new webpack.NoEmitOnErrorsPlugin(),
        // do not emit compiled assets that include errors

        // new BundleTracker({filename: "./webpack-stats.json"}),
    ],

    devServer: {
        host: "localhost",
        port: 3000,

        historyApiFallback: true,
        // respond to 404s with index.html

        headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
            "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization"
        },

        hot: true,
        // enable HMR on the server
    },
};
