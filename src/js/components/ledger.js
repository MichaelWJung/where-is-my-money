import React from "react";

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
          <TableRow key="0">
            <TableCell>12/31/20</TableCell>
            <TableCell>Salary</TableCell>
            <TableCell>Income:Salary</TableCell>
            <TableCell align="right">
              <input type="text" value="5000.00" class="ledger-number"/>
            </TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right">5000.00</TableCell>
          </TableRow>
          <TableRow key="1">
            <TableCell>01/03/21</TableCell>
            <TableCell>Grocerie shopping</TableCell>
            <TableCell>Expenses:Groceries</TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right">21.28</TableCell>
            <TableCell align="right">4978.72</TableCell>
          </TableRow>
          <TableRow key="2">
            <TableCell>01/13/21</TableCell>
            <TableCell>Cinema</TableCell>
            <TableCell>Expenses:Entertainment</TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right">32.00</TableCell>
            <TableCell align="right">4946.72</TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>;
}
