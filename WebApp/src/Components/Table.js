import './style/Table.css' 
import { useHistory } from 'react-router-dom';

import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import SettingsIcon from '@material-ui/icons/Settings';
import React, { useMemo } from 'react';
import { useTable } from 'react-table';
import axios from 'axios';
const COLUMNS=[
   {
      Header: "ID#",
      accessor:"id"
   },
   {
      Header: "Name",
      accessor:"name"
   },
   {
      Header: "Country",
      accessor:"address.country"
   },
   {
      Header: "Town",
      accessor:"address.town"
   },
   {
      Header: "Owner Company",
      accessor:"ownerCompanyName"
   },
   {
      Header: "Total spots",
      accessor:"spotsTotal"
   },
   {
      Header: "Spots taken",
      accessor:"spotsTaken"
   },
   {
      Header: "Price per hour",
      accessor:"pricePerHour"
   },
   {
      Header: "Date added",
      accessor:"addedDateTime"
   },
];

export const Table=(props)=>
{
const history=useHistory();
const columns=useMemo(()=>COLUMNS,[]);
const data=useMemo(()=>props.dat,[]);
const tab=useTable({columns,data});
const {getTableProps,getTableBodyProps,headerGroups,rows,prepareRow}=tab;
const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
const options = {
   headers: {'security-token': props.secure.state.token.current}
}
const handleEdit=(e)=>
{
   history.push('/newpark',{id:  e,secure: props.secure,isedit: true});
}
const handleClick=(e)=>
{
   history.push('/listingdetails',{id:  e,secure: props.secure })
}

const deleteParking = p => {
   console.log(p);
   axios.delete(`${api_url}/p/parkings/${p}`, 
   options).then(() => props.load(true));
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
                  <IconButton aria-label="delete" style={{ margin: 0, padding: 0,marginLeft:"5px" }} onClick={()=>{deleteParking(row.cells[0].value)}}>
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


