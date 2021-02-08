import './style/AdminPanel.css';
import {useHistory,useLocation} from "react-router";
import SearchIcon from '@material-ui/icons/Search';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import axios from 'axios';
import IconButton from '@material-ui/core/IconButton';
import { useState ,useEffect} from 'react';
function ParkerDetails() {
  const location=useLocation();
  const history = useHistory();
  const [data,setData]=useState([]);
  const [loading,setLoading]=useState(true);
  const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
  const options = {
    headers: {'security-token': location.state.secure.state.token.current}
  }
  useEffect( () => {
    axios.get(`${api_url}/p/bookings?parkingId=${location.state.id}`,options)
    .then((response) => {setData(response.data)}).finally(()=>setLoading(true));
},[]);
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
      <IconButton aria-label="search" style={{marginTop:"100px",marginLeft:"40px"}} onClick={()=>history.push("/listingdetails",{id: location.state.main,secure: location.state.secure})}>
        <ArrowBackIosIcon fontSize="large"/>
      </IconButton>
      </div>
      </div>
    </div>
  );
}

export default ParkerDetails;