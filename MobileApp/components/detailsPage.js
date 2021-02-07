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

export default function DetailsPage({navigation, route}) {

    const [name, setName] = useState(route.params.name);
    const [country, setCountry] = useState(route.params.country);
    const [town, setTown] = useState(route.params.town);
    const [streetName, setStreet] = useState(route.params.streetName);
    const [streetNumber, setStreetNumber] = useState(route.params.streetNumber);
    const [spotsTotal, setSpotsTotal] = useState(route.params.spotsTotal);
    const [firstName, setFirstName] = useState(route.params.firstName);
    const [lastName, setLasttName] = useState(route.params.lastName);
    const [companyName, setCompanyName] = useState(route.params.companyName);
    const [ownerCountry, setOwnerCountry] = useState(route.params.ownerCountry);
    const [ownerTown, setOwnerTown] = useState(route.params.ownerTown);
    const [ownerStreet, setOwnerStreet] = useState(route.params.ownerStreet);
    const [ownerStreetNumber, setOwnerStreetNumber] = useState(route.params.ownerStreetNumber);

    const [isEditingAddress, setAddressEditing] = useState(false);
    const [err, setErr] = useState("");

    const parking = route.params;
    
    const options = {
        headers: {'security-token': route.params.token}
    }

    const updateParking = () => {
        axios.put(`${api_url}/p/parkings/${parking.id}?createNewOwner=false`, {
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
        options).then(response => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
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

            <View>
                <TouchableOpacity onPress={updateParking}>
                    <Text>Update</Text>
                </TouchableOpacity>
            </View>
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