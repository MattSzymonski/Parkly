import './style/AdminPanel.css';
import {useHistory,useLocation} from "react-router";
import SearchIcon from '@material-ui/icons/Search';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import FilterListIcon from '@material-ui/icons/FilterList';
import axios from 'axios';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import SettingsIcon from '@material-ui/icons/Settings';
import { useState ,useEffect} from 'react';
import {ListofCustomers} from './ListOfCustomers';
function ListingDetails() {
  const location=useLocation();
  const history = useHistory();
  const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
  const [loading ,setLoading] = useState(true);
  const [can,setCan] = useState(true);
  const [bookings,setBook]=useState([]);
  const [data,setData]=useState([]);
  
  const options = {
    headers: {'security-token': location.state.secure.state.token.current}
  }

  const handleEdit=(e)=>
  {
   history.push('/newpark',{id:  e,secure: location.state.secure,isedit:true });
  }

  const deleteParking = p => {
    axios.delete(`${api_url}/p/parkings/${p}`, 
    options).then(()=>history.push("/home",{token: location.state.secure.state.token , user: location.state.secure.state.user}));
  }



  useEffect( () => {
    axios.get(`${api_url}/p/parkings/${location.state.id}`, options)
    .then((response) => {setData(response.data)}).finally(() => setLoading(false));

    axios.get(`${api_url}/p/bookings?parkingId=${location.state.id}&pageSize=1000`,options)
    .then((response) => {setBook(response.data)}).finally(()=>setCan(false));
},[loading]);

  return (
  <div>
    <div className="topcontainer">
      <div className="name1">Parkly</div>
      <div className="panelname">Administrative Panel</div>
      <div className="admin">{location.state.secure.state.user.current}</div>
      <div style={{flex:"5%"}}></div>
    </div>
    <div style={{display:"flex"}}>
      <div style={{flex: "11%"}}>
      <IconButton aria-label="search" style={{marginTop:"100px",marginLeft:"40px"}} onClick={()=>history.push("/home",{token: location.state.secure.state.token , user: location.state.secure.state.user})}>
        <ArrowBackIosIcon fontSize="large"/>
      </IconButton>
      </div>
      {loading? "Loading...":
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
          <image style={{flex:"15%"}}></image>
          <div style={{flex:"30%"}}>
            <div className="prop">
              <div className="smallcontainer">
                <p>Address</p>
              </div>
              <p>{data.address.streetName} {data.address.streetNumber}</p>
              <p>{data.address.town}</p>
            </div>
          </div>
          <div style={{flex:"30%"}}>
            <div className="prop">
              <div className="smallcontainer">
                <p>Owner Info</p>
              </div>
              <p>{data.parkingOwner.firstName} {data.parkingOwner.LastName}</p>
              <p>{data.parkingOwner.address.streetName} {data.parkingOwner.address.streetNumber}</p>
              <p>{data.parkingOwner.address.town}</p>
              <p>{data.parkingOwner.address.country}</p>
            </div>
          </div>
          <div style={{flex:"10%"}}>
          </div>
        </div>
        <div>
        <table>
          <tr>
            <th>ID#</th>
            <th>Name</th>
            <th>Country</th>
            <th>Town</th>
            <th>OwnerCompany</th>
            <th>Total spots</th>
            <th>Price per hour</th>
            <th>Date added</th>
            <th></th>
          </tr>
          <tr>
            <td>{data.id}</td>
            <td>{data.name}</td>
            <td>{data.address.country}</td>
            <td>{data.address.town}</td>
            <td>{data.parkingOwner.companyName}</td>
            <td>{data.spotsTotal}</td>
            <td>{data.pricePerHour}</td>
            <td>{data.addedDateTime}</td>
            <td>
                  <IconButton aria-label="setting" style={{ margin: 0, padding: 0,marginRight:"5px" }} onClick={()=>handleEdit(data.id)}>
                     <SettingsIcon/>
                  </IconButton>
                  <IconButton aria-label="delete" style={{ margin: 0, padding: 0,marginLeft:"5px" }} onClick={()=>deleteParking(data.id)}>
                     <DeleteIcon />
                  </IconButton>
               </td>
          </tr>
        </table>
        </div>
        <div style={{marginTop:"10px"}}>
          {can? "":
          <ListofCustomers da={bookings.items} secure={location} id={data.id} load={setLoading}/>}
        </div>
      </div>}
      <div style={{flex: "4%"}}></div>
  </div>
</div>
  );
}

export default ListingDetails;