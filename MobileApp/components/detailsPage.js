import React, {useState, useEffect} from 'react'
import {
    StyleSheet,
    Text,
    View,
    Image,
    TextInput,
    Button,
    TouchableOpacity,
  } from "react-native";

import axios from 'axios';

const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

const DetailsPage = ({navigation, route}) => {

    const [name, setName] = useState(route.params.item.name);
    const [country, setCountry] = useState(route.params.item.country);
    const [town, setTown] = useState(route.params.item.town);
    const [streetName, setStreet] = useState(route.params.item.streetName);
    const [streetNumber, setStreetNumber] = useState(route.params.item.streetNumber);
    const [spotsTotal, setSpotsTotal] = useState(route.params.item.spotsTotal);
    const [firstName, setFirstName] = useState(route.params.item.firstName);
    const [lastName, setLasttName] = useState(route.params.item.lastName);
    const [companyName, setCompanyName] = useState(route.params.item.companyName);
    const [ownerCountry, setOwnerCountry] = useState(route.params.item.ownerCountry);
    const [ownerTown, setOwnerTown] = useState(route.params.item.ownerTown);
    const [ownerStreet, setOwnerStreet] = useState(route.params.item.ownerStreet);
    const [ownerStreetNumber, setOwnerStreetNumber] = useState(route.params.item.ownerStreetNumber);

    const [isEditingAddress, setAddressEditing] = useState(false);
    const [err, setErr] = useState("");

    const parking = route.params;
    
    const options = {
        headers: {'security-token': route.params.token}
    }

    const updateParking = () => {
        axios.put(`${api_url}/p/parkings/${route.params.item.id}?createNewOwner=false`, {
            name: name,
            address: {
                country: country,
                town: town,
                streetName: streetName,
                streetNumber: streetNumber
            },
            spotsTotal: spotsTotal,
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
        },
        options).then((response) => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
    }

    return (
        <View style={styles.container}>

            <View style={styles.actionBar} >
                <Text>Address</Text>
                {isEditingAddress == false ? 
                <TouchableOpacity onPress={() => setAddressEditing(true)}><Text>Edit</Text></TouchableOpacity>
                :
                <TouchableOpacity onPress={() => setAddressEditing(false)}><Text>Save</Text></TouchableOpacity>
                }
            </View>

            <View>
                {isEditingAddress == false ? 
                <Text>{name}</Text>
                :
                <TextInput value={name} onChangeText={(val) => setName(val)}></TextInput>}
            </View>

     
                <TouchableOpacity onPress={updateParking} style={{borderRadius: 20, backgroundColor: "yellow", height: 40, width: 100, margin: 35}}>
                    <Text>Update</Text>
                   
                </TouchableOpacity>
  
        </View>
    )

} 

const styles = StyleSheet.create({
    
    container: {
        flex: 1,
        backgroundColor: "#fff",
    },

    actionBar: {
        backgroundColor: "#ffd300"
    },
});

export default DetailsPage;
