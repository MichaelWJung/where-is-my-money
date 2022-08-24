import React from 'react';
import LedgerRow from '../components/ledger_row';
import TableContainer from '@mui/material/TableContainer';
import TableBody from '@mui/material/TableBody';

export default {
  title: 'LedgerRow',
  component: LedgerRow,
};

const Template = (args) => (
  <TableContainer>
    <TableBody>
      <LedgerRow {...args} />
    </TableBody>
  </TableContainer>
);

export const InactiveRow = Template.bind({});
InactiveRow.args = {
  edit: false,
  date: "12/31/20",
  description: "Salary",
  transfer: "Income:Salary",
  debit: "5000.00",
  credit: "",
  balance: "7000.00",
  key: 0,
}

export const ActiveRow = Template.bind({});
ActiveRow.args = {
  ...InactiveRow.args,
  edit: true,
}
