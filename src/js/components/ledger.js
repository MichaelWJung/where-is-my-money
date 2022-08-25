import React from "react";
import PropTypes from "prop-types";

import LedgerRow from './ledger_row';
import InputAdornment from '@mui/material/InputAdornment';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TextField from '@mui/material/TextField';

export default function Ledger(props) {
  return <TableContainer>
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>Date</TableCell>
            <TableCell>Description</TableCell>
            <TableCell>Transfer</TableCell>
            <TableCell align="right">Debit</TableCell>
            <TableCell align="right">Credit</TableCell>
            <TableCell align="right">Balance</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {props.data.map(rowData => (
            <LedgerRow
              date={rowData.date}
              description={rowData.description}
              transfer={rowData.transfer}
              debit={rowData.debit}
              credit={rowData.credit}
              balance={rowData.balance}
              key={rowData.key}
              edit={rowData.key === props.edit} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>;
}

Ledger.propTypes = {
  data: PropTypes.arrayOf(PropTypes.object),
  edit: PropTypes.number,
};

Ledger.defaultProps = {
  data: [],
  edit: null,
};
