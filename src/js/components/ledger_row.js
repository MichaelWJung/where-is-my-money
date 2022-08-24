import React from "react";
import PropTypes from "prop-types";

import LedgerInput from "./ledger_input";
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';

export default function LedgerRow(props) {
  if (props.edit) {
    const noop = (e) => null;
    return (
      <TableRow key={props.key}>
        <TableCell><LedgerInput value={props.date} onCommit={noop} onChange={noop}/></TableCell>
        <TableCell><LedgerInput value={props.description} onCommit={noop} onChange={noop}/></TableCell>
        <TableCell><LedgerInput value={props.transfer} onCommit={noop} onChange={noop}/></TableCell>
        <TableCell align="right"><LedgerInput value={props.debit} onCommit={noop} onChange={noop}/></TableCell>
        <TableCell align="right"><LedgerInput value={props.credit} onCommit={noop} onChange={noop}/></TableCell>
        <TableCell align="right"><LedgerInput value={props.balance} onCommit={noop} onChange={noop}/></TableCell>
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
}
