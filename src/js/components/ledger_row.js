import React from "react";
import PropTypes from "prop-types";

import LedgerInput from "./ledger_input";
import TableCellLoad from '@mui/material/TableCell';
import TableRowLoad from '@mui/material/TableRow';

const TableCell = TableCellLoad.default ? TableCellLoad.default : TableCellLoad;
const TableRow = TableRowLoad.default ? TableRowLoad.default : TableRowLoad;

const noop = (e) => null;

export default function LedgerRow(props) {
  if (props.edit) {
    return (
      <TableRow key={props.key}>
        <TableCell><LedgerInput value={props.date} onCommit={noop} onChange={props.onChangeDate}/></TableCell>
        <TableCell><LedgerInput value={props.description} onCommit={noop} onChange={props.onChangeDescription}/></TableCell>
        <TableCell><LedgerInput value={props.transfer} onCommit={noop} onChange={props.onChangeTransfer}/></TableCell>
        <TableCell align="right"><LedgerInput value={props.debit} onCommit={noop} onChange={props.onChangeDebit}/></TableCell>
        <TableCell align="right"><LedgerInput value={props.credit} onCommit={noop} onChange={props.onChangeCredit}/></TableCell>
        <TableCell align="right">{props.balance}</TableCell>
      </TableRow>
    );
  }
  return (
    <TableRow key={props.key}>
      <TableCell>{props.date}</TableCell>
      <TableCell>{props.description}</TableCell>
      <TableCell>{props.transfer}</TableCell>
      <TableCell align="right">{props.debit}</TableCell>
      <TableCell align="right">{props.credit}</TableCell>
      <TableCell align="right">{props.balance}</TableCell>
    </TableRow>
  );
}

LedgerRow.propTypes = {
  edit: PropTypes.bool,
  date: PropTypes.string,
  description: PropTypes.string,
  transfer: PropTypes.string,
  debit: PropTypes.string,
  credit: PropTypes.string,
  balance: PropTypes.string,
  key: PropTypes.number,
  onChangeDate: PropTypes.func,
  onChangeDescription: PropTypes.func,
  onChangeTransfer: PropTypes.func,
  onChangeDebit: PropTypes.func,
  onChangeCredit: PropTypes.func,
}

LedgerRow.defaultProps = {
  edit: false,
  onChangeDate: () => null,
  onChangeDescription: () => null,
  onChangeTransfer: () => null,
  onChangeDebit: () => null,
  onChangeCredit: () => null,
}
