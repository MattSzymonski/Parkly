import './style/AdminPanel.css';
import React, { useState, useRef } from "react";
import {useHistory} from 'react-router'
import axios from 'axios';
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";
export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [fetching, setFetching] = useState(false);
  const [err, setErr] = useState("");
  const username2 = useRef("");
  const token = useRef("");
  const history=useHistory();

  const userLogin = () => {
    setErr("");
    setFetching(true);
    axios.post(`${api_url}/p/login`, {
      login: username,
      password: password
    }).then((response) =>  {setFetching(false); token.current = response.data; history.push('/home', {token: token, user: username2})}).catch((err) => {setErr(err); setFetching(false);})
  }


  return (
    <div>
    <div className="topcontainer">
      <div className="name1">Parkly</div>
      <div className="panelname">Parking made SIMPLE</div> 
      <div className="admin">Login</div>
      <div style={{flex:"5%"}}></div>
    </div>
    <div style={{height: "100px"}}></div>
    <div style={{display:"flex"}}>
        <div style={{flex: "40%"}}>

        </div>
        <div style={{flex: "20%"}}>
        <TextField
        style={{width:"100%"}}
            value={username}
          placeholder="username"
          
          onChange={(e)=>{setUsername(e.target.value);username2.current=e.target.value;}}
        />
    <TextField
    style={{width:"100%"}}
          placeholder="password"
          value={password}
          type="password"
          onChange={(e)=>{setPassword(e.target.value)}}
        />
    <Button  
    style={{marginTop:"20px",}}
          color="primary"
          variant="outlined"
          onClick={()=>userLogin()}
        >Login
        </Button>
        { fetching ? 
        <h3>Please Wait...</h3>
      :
      null
      }

     {
       err === "" ?
       null
       :
        <h3>Failed to login!</h3>
     }
        </div>
        <div style={{flex: "40%"}}>

        </div>
    </div>
    </div>

  );
  
}