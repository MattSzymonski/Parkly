import './style/AdminPanel.css';
import React, {useEffect, useState} from 'react';
import {Table} from'./Table.js'
import { useHistory,useLocation } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import FilterListIcon from '@material-ui/icons/FilterList';
import AddIcon from '@material-ui/icons/Add';
import axios from 'axios';

function AdminPanel() {

  const [data,setParkings] = useState([]);
  const [loading ,setLoading] = useState(true);
  const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
  const location=useLocation()
  const options = {
    headers: {'security-token': location.state.token.current}
}
  useEffect( () => {
    axios.get(`${api_url}/p/parkings?pageSize=1000`, options).then((response) => {setParkings(response.data)}).finally(() => setLoading(false))
},[loading]);

  const history=useHistory();
  const handleClick=()=>
  {
      history.push('/newpark',{secure: location, id: data.id,isedit: false});
  }


  return (
  <div>
    <div className="topcontainer">
      <div className="name1">Parkly</div>
      <div className="panelname">Administrative Panel</div> 
      <div className="admin">{location.state.user.current}</div>
      <div style={{flex:"5%"}}></div>
    </div>
    <div style={{display:"flex"}}>
      <div style={{flex: "11%"}}></div>
      <div style={{flex: "84%"}}>
          <div style={{marginTop:"10px",marginBottom:"10px",display:"flex"}}>
            <div style={{flex: "50%"}}>
              <input className="input1" ></input>
              <IconButton aria-label="search" className="buttons">
                     <SearchIcon/>
              </IconButton>
              <IconButton aria-label="filter" className="buttons">
                     <FilterListIcon/>
              </IconButton>
              <IconButton aria-label="add" className="buttons" onClick={()=>handleClick()}>
                     <AddIcon/>
              </IconButton>
            </div>
            <div style={{flex: "50%"}}></div>
          </div>
        <div>{
          loading? "Loading...": <Table dat={data.items} secure={location} load={setLoading}/>  }               
        </div>     
        </div>
      <div style={{flex: "4%"}}></div>
    </div>
</div>
  );
}

export default AdminPanel;