import './Table.css' 
import { useHistory } from 'react-router-dom';

import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import SettingsIcon from '@material-ui/icons/Settings';
import React, { useMemo } from 'react';
import { useTable } from 'react-table';
const COLUMNS=[
   {
      Header: "ID#",
      accessor:"id"
   },
   {
      Header: "Country",
      accessor:"country"
   },
   {
      Header: "Town",
      accessor:"town"
   },
   {
      Header: "Owner",
      accessor:"owner"
   },
   {
      Header: "Total spots",
      accessor:"total_spots"
   },
   {
      Header: "Spots taken",
      accessor:"spots_taken"
   },
   {
      Header: "Date added",
      accessor:"date_added"
   },
   {
      Header: "Date last modified",
      accessor:"date_last"
   },
];

export const Table=({dat})=>
{
const history=useHistory();
const columns=useMemo(()=>COLUMNS,[]);
const data=useMemo(()=>dat,[]);
const tab=useTable({columns,data});
const {getTableProps,getTableBodyProps,headerGroups,rows,prepareRow}=tab;

const handleEdit=(e)=>
{
   history.push( {pathname: '/editpark',state:  e });

}
const handleClick=(e)=>
{
   history.push('/listingdetails',{update:  e })
}

return(
<div>
   <table {...getTableProps}>
      <thead>
          {headerGroups.map(headerGroup => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                <th {...column.getHeaderProps()}>{column.render('Header')}</th>
              ))}
              <th></th>
            </tr>
          ))}
      </thead>
      <tbody {...getTableBodyProps()}>
          {rows.map(row => {
            prepareRow(row)
            return (
              <tr {...row.getRowProps()} >
                {row.cells.map(cell => {
                  return <td {...cell.getCellProps()} onClick={()=>handleClick(row.cells[0].value)} >{cell.render('Cell')}</td>
                })}
                <td>
                  <IconButton aria-label="setting" style={{ margin: 0, padding: 0,marginRight:"5px" }} onClick={()=>handleEdit(row.cells[0].value)}>
                     <SettingsIcon/>
                  </IconButton>
                  <IconButton aria-label="delete" style={{ margin: 0, padding: 0,marginLeft:"5px" }}>
                     <DeleteIcon />
                  </IconButton>
               </td>
              </tr>
            )
          })}
      </tbody>
   </table>
</div>

)
}


