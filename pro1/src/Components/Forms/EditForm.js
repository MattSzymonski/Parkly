import React,{useState} from 'react';
import './Form.css';
import {Validate} from "./Validate";
const defaultData={
  country:"",
  town:"",
  owner:"",
  address:"",
  postal:"",
  nip:"",
  companyName:"",
  addressInv:"",
  townInv:"",
  postalInv:"",
  bank:""
}
const EditForm = ({props}) => {

  const [istouched,settouch]=useState({country: false,town: false, owner: false,address:false,postal:false,
  nip:false,companyName:false,addressInv:false,townInv:false,postalInv:false,bank:false});
  const [data,setData]=useState(defaultData);
  const handlechange=e=>
  {
    settouch({...istouched,[e.target.name]: true});
    setData({...data,[e.target.name]:e.target.value});
  }
  return (
    <div>
      <form>
      <div style={{display: "flex"}}>
          <div style={{flex: "51%"}} className="top">
            <p>Info</p>
          </div>
          <div style={{flex: "39%"}}/>
     </div>
     <div style={{display:"flex"}}>
       <div style={{flex: "20%"}}>
         <div className="top">
          <p>Country</p>
         </div>
         <div className="top">
          <p>Town</p>
         </div>
         <div className="top">
          <p>Owner</p>
         </div>
         <div className="top">
          <p>Address</p>
         </div>
         <div className="top">
          <p>Postal code</p>
         </div>
       </div>
       <div style={{flex:"35%"}}>
         <div >
           <input name="country" value={data.country} onChange={handlechange}></input>
         </div>
         <div >
           <input name="town"  value={data.town} onChange={handlechange}></input>
         </div>
         <div >
           <input name="owner"  value={data.owner} onChange={handlechange}></input>
         </div>
         <div >
           <input name="address"  value={data.address} onChange={handlechange}></input>
         </div>
         <div>
           <input name="postal"  value={data.postal} onChange={handlechange}></input>
         </div>
       </div>
       <div style={{flex:"45%"}}>
       <div className="errors">
       {istouched.country ? Validate("1",data.country):""}
         </div>
         <div className="errors">
         {istouched.town ? Validate("1",data.town):""}
         </div>
         <div className="errors">
         {istouched.owner ? Validate("1",data.owner):""}
         </div>
         <div className="errors">
         {istouched.address ? Validate("1",data.address):""}
         </div>
         <div className="errors">
         {istouched.postal ? Validate("2",data.postal):""}
         </div>
       </div>
     </div>
     <div style={{display: "flex"}}>
          <div style={{flex: "51%"}} className="top">
            <p>Invoice Info</p>
          </div>
          <div style={{flex: "39%"}}/>
     </div>
     <div style={{display:"flex"}}>
       <div style={{flex: "20%"}}>
         <div className="top">
          <p>NIP</p>
         </div>
         <div className="top">
          <p>Company Name</p>
         </div>
         <div className="top">
          <p>Address</p>
         </div>
         <div className="top">
          <p>Town</p>
         </div>
         <div className="top">
          <p>Postal code</p>
         </div>
         <div className="top">
          <p>Bank No.</p>
         </div>
       </div>
       <div style={{flex:"35%"}}>
         <div >
           <input name="nip"  value={data.nip} onChange={handlechange}></input>
         </div>
         <div >
           <input name="companyName" value={data.companyName} onChange={handlechange}></input>
         </div>
         <div >
           <input name="addressInv" value={data.addressInv} onChange={handlechange}></input>
         </div>
         <div >
           <input name="townInv" value={data.townInv} onChange={handlechange}></input>
         </div>
         <div>
           <input name="postalInv" value={data.postalInv} onChange={handlechange}></input>
         </div>
         <div>
           <input name="bank" value={data.bank} onChange={handlechange}></input>
         </div>
       </div>
       <div style={{flex:"45%"}}>
        <div className="errors">
          {istouched.nip ? Validate("3",data.nip):""}
         </div>
         <div className="errors">
         {istouched.companyName ? Validate("1",data.companyName):""}
         </div>
         <div className="errors">
         {istouched.addressInv ? Validate("1",data.addressInv):""}
         </div>
         <div className="errors">
         {istouched.townInv ? Validate("1",data.townInv):""}
         </div>
         <div className="errors">
         {istouched.postalInv ? Validate("2",data.postalInv):""}
         </div>
         <div className="errors">
         {istouched.bank ? Validate("3",data.bank):""}
         </div>
       </div>
    </div>
    </form>
    </div>
  );
};

export default EditForm;