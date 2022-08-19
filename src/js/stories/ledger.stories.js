import React from 'react';
import Ledger from '../components/ledger';

export default {
  title: 'Ledger',
  component: Ledger,
};

const Template = (args) => <Ledger {...args} />;

export const AccountOverview = Template.bind({});
