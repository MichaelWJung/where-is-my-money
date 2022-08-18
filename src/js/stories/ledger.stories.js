import React from 'react';
import Ledger from '../components/ledger'

export default {
  /* 👇 The title prop is optional.
  * See https://storybook.js.org/docs/react/configure/overview#configure-story-loading
  * to learn how to generate automatic titles
  */
  title: 'Ledger',
  component: Ledger,
};

const Template = (args) => <Ledger {...args} />;

export const AccountOverview = Template.bind({});
AccountOverview.args = {
  active: true
};
