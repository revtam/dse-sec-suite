/* eslint-disable @typescript-eslint/no-var-requires */

const path = require('path')
const webpack = require('webpack')
const Dotenv = require('dotenv-webpack')

const CopyPlugin = require('copy-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin')

const isProduction = process.env.MODE === 'production'
const isDevelopment = !isProduction

const CSSLoader = {
  test: /\.css$/,
  use: [
    !isProduction && 'cache-loader',
    isProduction ? MiniCssExtractPlugin.loader : 'style-loader',
    {
      loader: 'css-loader',
      options: {
        modules: {
          auto: true,
          exportLocalsConvention: 'camelCase'
        },
        importLoaders: true
      }
    }
  ].filter(Boolean)
}

const plugins = [
  isProduction && new MiniCssExtractPlugin(),
  new HtmlWebpackPlugin({
    filename: 'index.html',
    template: path.resolve(__dirname, './public/index.html'),
    inject: 'head'
  }),
  isDevelopment && new webpack.HotModuleReplacementPlugin(),
  isDevelopment && new ReactRefreshWebpackPlugin(),
  isProduction && new CleanWebpackPlugin(),
  isProduction &&
  new CopyPlugin({
    patterns: [
      {
        from: 'public',
        globOptions: {
          ignore: ['**/index.html']
        },
        noErrorOnMissing: true,
      }
    ]
  }),
  new Dotenv()
].filter(Boolean)

module.exports = {
  entry: './src/index.tsx',
  output: {
    path: path.resolve(__dirname, 'dist/'),
    filename: '[name].[contenthash:8].js',
    chunkFilename: isDevelopment ? '[name].chunk.js' : '[name].[contenthash:8].chunk.js',
    publicPath: '/',
    pathinfo: isDevelopment
  },
  mode: process.env.MODE ?? 'development',
  optimization: {
    minimize: isProduction,
    usedExports: true,
    splitChunks: {
      chunks: 'all'
    }
  },
  resolve: {
    alias: {
      '@': path.join(__dirname, './src'),
      assets: path.join(__dirname, './assets')
    },
    extensions: ['.tsx', '.ts', '.js']
  },

  module: {
    rules: [
      {
        test: /\.(tsx?)$/,
        include: path.resolve(__dirname, 'src'),
        use: ['cache-loader', 'ts-loader']
      },
      CSSLoader,
      {
        test: /\.(jpg|jpeg|png|svg)$/,
        use: 'file-loader'
      }
    ]
  },
  devServer: {
    contentBase: path.join(__dirname, 'public/'),
    port: 8080,
    host: '0.0.0.0',
    publicPath: '/',
    historyApiFallback: true,
    hot: true
  },
  devtool: isDevelopment && 'eval-source-map',
  plugins
}
