const webpack = require('webpack');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
  mode: 'development',
  entry: {
    javascript: "./app/js/client.js"
  },
  output: {
    path: __dirname + "/src/main/resources/static",
    filename: "bundle.js"
  },
  module: {
    rules: [
      {
        test: /\.scss$/,
        use: [ MiniCssExtractPlugin.loader, "css-loader", "sass-loader" ]
      },
      {test: /\.js$/, exclude: /node_modules/, loader: "babel-loader"},
      {test: /\.jsx$/, exclude: /node_modules/, loader: "babel-loader"}
    ]
  },
  plugins: [
    new webpack.IgnorePlugin(/^(buffertools)$/), // unwanted "deeper" dependency
    new MiniCssExtractPlugin({filename: 'app/styles/app.css', allChunks: true})
  ]
};
