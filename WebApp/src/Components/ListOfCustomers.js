import './style/Table.css' 
import { useHistory,useLocation } from 'react-router-dom';
import axios from 'axios';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import SettingsIcon from '@material-ui/icons/Settings';
import React, { useMemo,useState,useEffect } from 'react';
import { useTable } from 'react-table';
const COLUMNS=[
   {
      Header: "ID#",
      accessor:"id"
   },
   {
      Header: "UserId",
      accessor:"userId"
   },
   {
      Header: "UserFirstName",
      accessor:"userFirstName"
   },
   {
      Header: "UserLastName",
      accessor:"userLastName"
   },
   {
      Header: "ParingId",
      accessor:"parkingId"
   },
   {
      Header: "ParkingName",
      accessor:"parkingName"
   },
   {
      Header: "Start Date",
      accessor:"startDateTime"
   },
];

export const ListofCustomers=props=>
{
const history=useHistory();
const columns=useMemo(()=>COLUMNS,[]);
const data=useMemo(()=>props.da,[]);
const tab=useTable({columns,data});
const {getTableProps,getTableBodyProps,headerGroups,rows,prepareRow}=tab;
const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
const options = {
   headers: {'security-token': props.secure.state.secure.state.token.current}
}

const handleClick=(e)=>
{
   history.push('/parker',{id:  e,main: props.id,secure: props.secure.state.secure })
}
const deleteBooking=z=>
{
  axios.delete(`${api_url}/p/bookings/${z}`, 
  options).then(()=>props.load(true));
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
                  <IconButton aria-label="delete" style={{ margin: 0, padding: 0,marginLeft:"5px" }} onClick={()=>deleteBooking(row.cells[0].value)}>
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


