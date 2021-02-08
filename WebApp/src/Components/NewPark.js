import './style/AdminPanel.css';
import React, { useEffect, useState} from 'react';
import { useHistory, useLocation } from 'react-router';
import Button from "@material-ui/core/Button";
import {Validate} from './Validate';
import './style/Form.css';
import axios from 'axios';
const defaultdata={
  id: 0,
  name: "",
        address: {
            country: "",
            town: "",
            streetName: "",
            streetNumber: ""
        },
        spotsTotal: 0,
        pricePerHour: 0,
        parkingOwner: {
            firstName: "",
            lastName: "",
            companyName: "",
            address: {
                country: "",
                town: "",
                streetName: "",
                streetNumber: ""
            }
        }
}
const NewPark=()=>
{
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [town, setTown] = useState("");
    const [streetName, setStreet] = useState("");
    const [streetNumber, setStreetNumber] = useState("");
    const [spotsTotal, setSpotsTotal] = useState(0);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLasttName] = useState("");
    const [companyName, setCompanyName] = useState("");
    const [ownerCountry, setOwnerCountry] = useState("");
    const [ownerTown, setOwnerTown] = useState("");
    const [ownerStreet, setOwnerStreet] = useState("");
    const [ownerStreetNumber, setOwnerStreetNumber] = useState("");
    const [pricePerHour, setPricePerHour] = useState(0);
  
    const [data,setData]=useState(defaultdata);
    const [erro,setErr]=useState("");
    const [istouched,settouch]=useState({country: false,town: false, owner: false,address:false,postal:false,
    nip:false,companyName:false,addressInv:false,townInv:false,postalInv:false,bank:false});
    const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
    const location=useLocation();
    const options = {
      headers: {'security-token': location.state.secure.state.token.current}
    }
  
    const handleClick=()=>
    {
      if(location.state.isedit)
      {
      axios.post(`${api_url}/p/parkings/${location.state.id}`,
      {
        name: name,
        address: {
            country: country,
            town: town,
            streetName: streetName,
            streetNumber: streetNumber
        },
        spotsTotal: spotsTotal,
        pricePerHour: pricePerHour,
        parkingOwner: {
            firstName: firstName,
            lastName: lastName,
            companyName: companyName,
            address: {
                country: ownerCountry,
                town: ownerTown,
                streetName: ownerStreet,
                streetNumber: ownerStreetNumber
            }
        }
    }
      , options)
      .then(() => history.push("/home",{token: location.state.secure.state.token,user:location.state.secure.state.user})).catch((err) => {setErr(err);});
      }
      else{
        axios.post(`${api_url}/p/parkings`,
        {
          name: name,
          address: {
              country: country,
              town: town,
              streetName: streetName,
              streetNumber: streetNumber
          },
          spotsTotal: spotsTotal,
          pricePerHour: pricePerHour,
          parkingOwner: {
              firstName: firstName,
              lastName: lastName,
              companyName: companyName,
              address: {
                  country: ownerCountry,
                  town: ownerTown,
                  streetName: ownerStreet,
                  streetNumber: ownerStreetNumber
              }
          }
      }      
        , options)
      .then(() => history.push("/home",{token: location.state.secure.state.token , user: location.state.secure.state.user}));
      }
    }
    const isValid=props=>
    {
    if(props==null || props.length<1)
    {
        return false;
    }
      return true;
    }

    useEffect(()=>
      {
      if (location.state.isedit)
      {
        axios.get(`${api_url}/p/parkings/${location.state.id}`, options)
        .then((response) => {setData(response.data)}).then(()=>{
          
          setName(data.name);
          setCountry(data.address.country);
          setTown(data.address.town);
          setStreet(data.address.streetName);
          setStreetNumber(data.address.streetNumber);
          setSpotsTotal(data.spotsTotal);
          setFirstName(data.owner.firstName);
          setLasttName(data.owner.lastName);
          setCompanyName(data.owner.companyName);
          setOwnerCountry(data.owner.address.country);
          setOwnerTown(data.owner.address.town);
          setOwnerStreet(data.owner.address.streetName);
          setOwnerStreetNumber(data.owner.address.streetNumber);
          setPricePerHour(data.pricePerHour);
        })        
      }
      },[data.name]);

    const history=useHistory();
  return (
    <div>
        <div className="topcontainer">
            <div className="name1">Parkly</div>
            <div className="panelname">Administrative Panel</div>
            <div className="admin">{location.state.secure.state.user.current}</div>
            <div style={{flex:"5%"}}></div>
        </div>
        <div style={{display:"flex",marginTop:"30px"}} >
            <div style={{flex: "11%"}}>
            </div>
            <div style={{flex: "84%"}}>
                <div style={{display:"flex"}}>
                    <div style={{flex:"65%"}}>

                    <form>
      <div style={{display: "flex"}}>
          <div style={{flex: "51%"}} className="top">
            <p>Parking Info</p>
          </div>
          <div style={{flex: "39%"}}/>
     </div>
     <div style={{display:"flex"}}>
       <div style={{flex: "20%"}}>
         <div className="top">
          <p>Name</p>
         </div>
         <div className="top">
          <p>SpotsTotal</p>
         </div>
         <div className="top">
          <p>pricePerHour</p>
         </div>
         <div className="top">
          <p>Country</p>
         </div>
         <div className="top">
          <p>Town</p>
         </div>
         <div className="top">
          <p>StretName</p>
         </div>
         <div className="top">
          <p>StreetNumber</p>
         </div>
       </div>
       <div style={{flex:"35%"}}>
         <div >
           <input name="name" value={name} onChange={(e)=>setName(e.target.value)}></input>
         </div>
         <div >
           <input name="spotsTotal"  value={spotsTotal} onChange={(e)=>setSpotsTotal(parseInt(e.target.value))}></input>
         </div>
         <div >
           <input name="pricePerHour"  value={pricePerHour} onChange={(e)=>setPricePerHour(parseInt(e.target.value))}></input>
         </div>
         <div >
           <input name="country"  value={country} onChange={(e)=>setCountry(e.target.value)}></input>
         </div>
         <div>
           <input name="town"  value={town} onChange={(e)=>setTown(e.target.value)}></input>
         </div>
         <div>
           <input name="streetName"  value={streetName} onChange={(e)=>setStreet(e.target.value)}></input>
         </div>
         <div>
           <input name="streetNumber"  value={streetNumber} onChange={(e)=>setStreetNumber(e.target.value)}></input>
         </div>
       </div>
       <div style={{flex:"45%"}}>
       <div className="errors">
          {istouched.nip ? Validate("1",name):""}
         </div>
       </div>
     </div>
     <div style={{display: "flex"}}>
          <div style={{flex: "51%"}} className="top">
            <p>Parking Owner Info</p>
          </div>
          <div style={{flex: "39%"}}/>
     </div>
     <div style={{display:"flex"}}>
       <div style={{flex: "20%"}}>
         <div className="top">
          <p>FirstName</p>
         </div>
         <div className="top">
          <p>LastName</p>
         </div>
         <div className="top">
          <p>CompanyName</p>
         </div>
         <div className="top">
          <p>Country</p>
         </div>
         <div className="top">
          <p>Town</p>
         </div>
         <div className="top">
          <p>StretName</p>
         </div>
         <div className="top">
          <p>StreetNumber</p>
          </div>
       </div>
       <div style={{flex:"35%"}}>
         <div >
           <input name="FirstName"  value={firstName} onChange={(e)=>setFirstName(e.target.value)}></input>
         </div>
         <div >
           <input name="LastName" value={lastName} onChange={(e)=>setLasttName(e.target.value)}></input>
         </div>
         <div >
           <input name="CompanyName" value={companyName} onChange={(e)=>setCompanyName(e.target.value)}></input>
         </div>
         <div >
           <input name="Country" value={ownerCountry} onChange={(e)=>setOwnerCountry(e.target.value)}></input>
         </div>
         <div>
           <input name="Town" value={ownerTown} onChange={(e)=>setOwnerTown(e.target.value)}></input>
         </div>
         <div>
           <input name="StreetName" value={ownerStreet} onChange={(e)=>setOwnerStreet(e.target.value)}></input>
         </div>
         <div>
           <input name="StreetNumber" value={ownerStreetNumber} onChange={(e)=>setOwnerStreetNumber(e.target.value)}></input>
         </div>
       </div>
       <div style={{flex:"45%"}}>
       </div>
    </div>
    </form>


                    </div>
                    <div style={{flex: "35%"}}>

                    </div>
                </div>
                <div style={{marginTop:"20px"}}>
                <Button 
          color="secondary"
          variant="outlined"
          style={{ marginRight: "1rem" }}
          onClick={()=>history.push("/home",{token: location.state.secure.state.token , user: location.state.secure.state.user})}
        >Cancel
        </Button>
        <Button  disabled={!isValid(name)}
          color="primary"
          variant="outlined"
          onClick={handleClick}
        >Submit
        </Button>
        </div>
            </div>
            <div style={{flex: "4%"}}></div>
        </div>
    </div>
    );
}
export default NewPark;
