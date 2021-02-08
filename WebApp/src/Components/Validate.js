import React from "react";
import "./style/validate.css"
export const Validate=(name,value)=>
{
var rezip = /^\d\d-\d\d\d$/;
switch (name) {
    case "1":
        if(value.length<1){
        return(
       <div className='error'><p>This field is required</p></div>);
        }
        break;
    case "2":
        if(value.length<1)
        {
        return(
            <div className='error'><p>This field is required</p></div>);          
        }
        else if(!rezip.test(value))
        {        
        return(
            <div className='error'><p>Invalid format</p></div>);
        }
       break;
    case "3":
        if(value.length<1)
        {
        return(
            <div className='error'><p>This field is required</p></div>);          
        }
        break;
    default:
        break;
  }
}
