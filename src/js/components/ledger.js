import React from "react";
import PropTypes from "prop-types";

import LedgerRow from './ledger_row';
import TableLoad from '@mui/material/Table';
import TableBodyLoad from '@mui/material/TableBody';
import TableCellLoad from '@mui/material/TableCell';
import TableContainerLoad from '@mui/material/TableContainer';
import TableHeadLoad from '@mui/material/TableHead';
import TableRowLoad from '@mui/material/TableRow';

const Table = TableLoad.default ? TableLoad.default : TableLoad;
const TableBody = TableBodyLoad.default ? TableBodyLoad.default : TableBodyLoad;
const TableCell = TableCellLoad.default ? TableCellLoad.default : TableCellLoad;
const TableContainer =
  TableContainerLoad.default ? TableContainerLoad.default : TableContainerLoad;
const TableHead = TableHeadLoad.default ? TableHeadLoad.default : TableHeadLoad;
const TableRow = TableRowLoad.default ? TableRowLoad.default : TableRowLoad;

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
