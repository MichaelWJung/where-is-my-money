import React from 'react';
import Ledger from '../components/ledger';

export default {
  title: 'Ledger',
  component: Ledger,
};

const Template = (args) => <Ledger {...args} />;

export const NoActiveRow = Template.bind({});
NoActiveRow.args = {
  data: [
    {
      date: "12/31/20",
      description: "Salary",
      transfer: "Income:Salary",
      debit: "5000.00",
      credit: "",
      balance: "5000.00",
      key: 0,
    },
    {
      date: "01/03/21",
      description: "Grocerie shopping",
      transfer: "Expenses:Groceries",
      debit: "",
      credit: "21.28",
      balance: "4978.72",
      key: 1,
    },
    {
      date: "01/13/21",
      description: "Cinema",
      transfer: "Expenses:Entertainment",
      debit: "",
      credit: "32",
      balance: "4946.72",
      key: 2,
    },
    {
      date: "01/16/21",
      description: "Birthday present",
      transfer: "Expenses:Gift",
      debit: "",
      credit: "100",
      balance: "4846.72",
      key: 3,
    },
  ],
  edit: null,
}

export const ActiveRow = Template.bind({});
ActiveRow.args = {
  ...NoActiveRow.args,
  edit: 1,
}
