module.exports = {
  stories: [
    '../src/js/stories/**/*.stories.js'
  ],
  addons: [
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    "@storybook/addon-interactions",
    "@storybook/preset-create-react-app"
  ],
  "framework": "@storybook/react",
  "core": {
    "builder": "@storybook/builder-webpack5"
  }
};
