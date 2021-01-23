import './AdminPanel.css';
import React, { useState} from 'react';
import { useLocation } from 'react-router';
import FormInfo from './Forms/FormInfo';
import FormInv from './Forms/FormInfo';
const EditPark=props=>
{
    console.log(props);
    const location=useLocation();
    console.log(location)
  return (
    <div>
        <div className="topcontainer">
            <div className="name1">Parkly</div>
            <div className="panelname">Administrative Panel</div>
            <div className="admin">Admin</div>
            <div style={{flex:"5%"}}></div>
        </div>
        <div style={{display:"flex",marginTop:"30px"}} >
            <div style={{flex: "11%"}}></div>
            <div style={{flex: "84%"}}>
                <div style={{display:"flex"}}>
                    <div style={{flex:"65%"}}>
                        <FormInfo/>
                    </div>
                    <div style={{flex: "35%"}}>
                        <div style={{display:"flex"}}>
                            <div style={{flex:"70%"}}>
                            </div>
                            <div style={{flex:"30%"}}>
                                <div className="prop" style={{width:"100%"}}>
                                    <div className="smallcontainer">
                                        <p>Free Spots</p>
                                    </div>
                                    <p style={{textAlign:"center",verticalAlign:"middle"}}>6</p>
                                </div>
                            </div>
                         </div>
                         <div style={{height:"100px",marginTop:"100px",width:"100%",backgroundColor:"yellow"}}>
                             tu obrazek
                         </div>
                    </div>
                </div>
            </div>
            <div style={{flex: "4%"}}></div>
        </div>
    </div>
    );
}
export default EditPark;