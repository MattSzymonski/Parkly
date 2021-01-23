import './AdminPanel.css';
import {Table} from'./Table.js'
import { useHistory } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import FilterListIcon from '@material-ui/icons/FilterList';
import AddIcon from '@material-ui/icons/Add';
function AdminPanel() {

  var data=[{"id":33,"country":"pol","town":"Radom","owner":"sb","total_spots":10,"spots_taken":5,"date_added":444,"date_last":4444},
  {"id":56,"country":"pol","town":"Radom","owner":"sb","total_spots":10,"spots_taken":5,"date_added":444,"date_last":4444}];
  const history=useHistory();
  const handleClick=()=>
  {
      history.push( {pathname: '/newpark' });
  }
  return (
  <div>
    <div className="topcontainer">
      <div className="name1">Parkly</div>
      <div className="panelname">Administrative Panel</div> 
      <div className="admin">Admin</div>
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
        <div>
          <Table dat={data}/>         
        </div>     
        </div>
      <div style={{flex: "4%"}}></div>
    </div>
</div>
  );
}

export default AdminPanel;