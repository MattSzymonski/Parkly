import './AdminPanel.css';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import FilterListIcon from '@material-ui/icons/FilterList';
import AddIcon from '@material-ui/icons/Add';
import {Table} from'./Table.js'
function ListingDetails() {

  var data=[];

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
            </div>
            <div style={{flex: "50%"}}></div>
            </div>
        <div style={{display:"flex",heigh:"160px"}}>
          <image style={{flex:"30%"}}></image>
          <div style={{flex:"30%"}}>
            <div className="prop">
              <div className="smallcontainer">
                <p>Address</p>
              </div>
              <p>sss</p>
              <p>wss</p>
              <div className="smallcontainer">
                <p>Bank No.</p>
              </div>
              <p>24242</p>
            </div>
          </div>
          <div style={{flex:"30%"}}>
            <div className="prop">
              <div className="smallcontainer">
                <p>Invoice Information</p>
              </div>
              <p>333</p>
              <p>323</p>
              <p>c3232</p>
              <p>42</p>
            </div>
          </div>
          <div style={{flex:"10%"}}>
            <div className="prop">
              <div className="smallcontainer">
                <p>Free Spots</p>
              </div>
              <p style={{textAlign:"center"}}>7</p>
            </div>
          </div>
        </div>
        <div>
          {data.length==0 ? "": <Table/>}
        </div>
       
        </div>
      <div style={{flex: "4%"}}></div>
    </div>
</div>
  );
}

export default ListingDetails;