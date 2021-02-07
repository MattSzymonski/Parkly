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
import { ScrollView } from 'react-native-gesture-handler';

import axios from 'axios';


const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

const DetailsPage = ({navigation, route}) => {

    const [name, setName] = useState(route.params.item.name);
    const [country, setCountry] = useState(route.params.item.address.country);
    const [town, setTown] = useState(route.params.item.address.town);
    const [streetName, setStreetName] = useState(route.params.item.address.streetName);
    const [streetNumber, setStreetNumber] = useState(route.params.item.address.streetNumber);
    const [spotsTotal, setSpotsTotal] = useState(route.params.item.spotsTotal);
    const [firstName, setFirstName] = useState(route.params.item.firstName);
    const [lastName, setLastName] = useState(route.params.item.lastName);
    const [companyName, setCompanyName] = useState(route.params.item.companyName);
    const [ownerCountry, setOwnerCountry] = useState(route.params.item.ownerCountry);
    const [ownerTown, setOwnerTown] = useState(route.params.item.ownerTown);
    const [ownerStreet, setOwnerStreet] = useState(route.params.item.ownerStreet);
    const [ownerStreetNumber, setOwnerStreetNumber] = useState(route.params.item.ownerStreetNumber);

    const [isEditingAddress, setAddressEditing] = useState(false);
    const [isInvoiceEditing, setInvoiceEditing] = useState(false);
    const [err, setErr] = useState("");
    
    const options = {
        headers: {'security-token': route.params.token.current}
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

    const deleteParking = () => {
        axios.delete(`${api_url}/p/parkings/${route.params.item.id}`, 
        options).then((response) => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
    }

    return (
        <View style={styles.container}>

            <ScrollView>
                <View style={styles.actionBar} >
                    <Text style={styles.standardText}>Address</Text>
                    
                    {isEditingAddress == false ? 
                    <TouchableOpacity onPress={() => setAddressEditing(true)}><Text style={styles.editSaveFunc}>Edit</Text></TouchableOpacity>
                    :
                    <TouchableOpacity onPress={() => setAddressEditing(false)}><Text style={styles.editSaveFunc}>Save</Text></TouchableOpacity>
                    }
                </View>
                
                <View>
                    {isEditingAddress == false ? 
                    <Text style={styles.standardText}>{name}</Text>
                    :
                    <TextInput style={styles.TextInput} value={name} onChangeText={(val) => setName(val)}></TextInput>}
                </View>
                <View>
                    {isEditingAddress == false ? 
                    <Text style={styles.standardText}>{country}</Text>
                    :
                    <TextInput style={styles.TextInput} value={country} onChangeText={(val) => setCountry(val)}></TextInput>}
                </View>
                <View>
                    {isEditingAddress == false ? 
                    <Text style={styles.standardText}>{streetName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={streetName} onChangeText={(val) => setStreetName(val)}></TextInput>}
                </View>
                <View>
                    {isEditingAddress == false ? 
                    <Text style={styles.standardText}>{streetNumber}</Text>
                    :
                    <TextInput style={styles.TextInput} value={streetNumber} onChangeText={(val) => setStreetNumber(val)}></TextInput>}
                </View>
                <View>
                    {isEditingAddress == false ? 
                    <Text style={styles.standardText}>{town}</Text>
                    :
                    <TextInput style={styles.TextInput} value={town} onChangeText={(val) => setTown(val)}></TextInput>}
                </View>
                
                <View style={styles.actionBar} >
                    <Text style={styles.standardText}>Invoice Information</Text>
                    {isInvoiceEditing == false ? 
                    <TouchableOpacity onPress={() => setInvoiceEditing(true)}><Text style={styles.editSaveFunc}>Edit</Text></TouchableOpacity>
                    :
                    <TouchableOpacity onPress={() => setInvoiceEditing(false)}><Text style={styles.editSaveFunc}>Save</Text></TouchableOpacity>
                    }
                </View>

                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{firstName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={firstName} onChangeText={(val) => setFirstName(val)}></TextInput>}
                </View>
                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{lastName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={lastName} onChangeText={(val) => setLastName(val)}></TextInput>}
                </View>
                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{ownerCountry}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerCountry} onChangeText={(val) => setOwnerCountry(val)}></TextInput>}
                </View>
                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{ownerStreet}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerStreet} onChangeText={(val) => setOwnerStreet(val)}></TextInput>}
                </View>
                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{ownerStreetNumber}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerStreetNumber} onChangeText={(val) => setOwnerStreetNumber(val)}></TextInput>}
                </View>
                <View>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.standardText}>{ownerTown}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerTown} onChangeText={(val) => setOwnerTown(val)}></TextInput>}
                </View>


            </ScrollView>
            <View style={styles.buttonHolder}>
                <TouchableOpacity onPress={updateParking} style={styles.actionButton}>
                    <Text style={styles.standardText}>Update</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={deleteParking} style={styles.actionButton}>
                    <Text style={styles.standardText}>Delete</Text>
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

    standardText: {
        fontSize: 20,
        margin: 10
    },

    editSaveFunc: {
        fontSize: 20,
        margin: 10
    },

    buttonHolder: 
    {
        flex: 1,
        flexDirection: "row",
        justifyContent: "space-between",
        position: 'absolute',
        bottom: 10
    },

    actionBar: {
        backgroundColor: "#ffd300",
        flex: 1,
        flexDirection: "row",
        justifyContent: "space-between"
    },

    TextInput: {
        height: 50,
        paddingLeft: 30,
        backgroundColor: "#f8f8ff",
        borderRadius: 20,
      },

    actionButton: {
        borderRadius: 10, 
        height: 40, 
        width: "40%", 
        marginHorizontal: 15,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#ffd300",
    }
});

export default DetailsPage;
