const webpack = require('webpack');

module.exports = {
    mode: 'development',
    entry: {
        javascript: "./app/js/client.js",
    },
    output: {
        path: __dirname + "/src/main/resources/static",
        filename: "bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.scss$/,
                use: ['style-loader', 'css-loader', 'sass-loader'],
            },
            {test: /\.js$/, exclude: /node_modules/, loader: "babel-loader"},
            {test: /\.jsx$/, exclude: /node_modules/, loader: "babel-loader"}
        ]
    },
    plugins: [
        new webpack.IgnorePlugin(/^(buffertools)$/) // unwanted "deeper" dependency
    ]
};
