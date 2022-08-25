import React, {useState} from 'react';
import LedgerRow from '../components/ledger_row';
import TableContainer from '@mui/material/TableContainer';
import TableBody from '@mui/material/TableBody';

export default {
  title: 'LedgerRow',
  component: LedgerRow,
};

const Template = (args) => {
  const [date, setDate] = useState(args.date ?? '');
  const [description, setDescription] = useState(args.description ?? '');
  const [transfer, setTransfer] = useState(args.transfer ?? '');
  const [debit, setDebit] = useState(args.debit ?? '');
  const [credit, setCredit] = useState(args.credit ?? '');
  return (
    <TableContainer>
      <TableBody>
        <LedgerRow {...args}
          onChangeDate={(...params) => {args.onChangeDate(...params); setDate(...params);}}
          onChangeDescription={(...params) => {args.onChangeDescription(...params); setDescription(...params);}}
          onChangeTransfer={(...params) => {args.onChangeTransfer(...params); setTransfer(...params);}}
          onChangeDebit={(...params) => {args.onChangeDebit(...params); setDebit(...params);}}
          onChangeCredit={(...params) => {args.onChangeCredit(...params); setCredit(...params);}}
          date={date}
          description={description}
          transfer={transfer}
          debit={debit}
          credit={credit}
          />
      </TableBody>
    </TableContainer>
  );
};

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
