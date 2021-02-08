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

    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [town, setTown] = useState("");
    const [streetName, setStreetName] = useState("");
    const [streetNumber, setStreetNumber] = useState("");
    const [spotsTotal, setSpotsTotal] = useState("");
    const [pricePerHour, setPPH] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [companyName, setCompanyName] = useState("");
    const [ownerCountry, setOwnerCountry] = useState("");
    const [ownerTown, setOwnerTown] = useState("");
    const [ownerStreet, setOwnerStreet] = useState("");
    const [ownerStreetNumber, setOwnerStreetNumber] = useState("");

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
        },
        options).then((response) => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
    }

    const deleteParking = () => {
        axios.delete(`${api_url}/p/parkings/${route.params.item.id}`, 
        options).then((response) => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
    }

    const fetchParking = () => {
        axios.get(`${api_url}/p/parkings/${route.params.item.id}`, options).then( (response) => {fillStates(response.data)}).finally( () => {setLoading(false);})
    }

    const fillStates = (data) => {
        setName(data.name);
        setCountry(data.address.country);
        setTown(data.address.town);
        setStreetName(data.address.streetName);
        setStreetNumber(data.address.streetNumber);
        setSpotsTotal(data.spotsTotal);
        setPPH(data.pricePerHour)
        setFirstName(data.parkingOwner.firstName);
        setLastName(data.parkingOwner.lastName);
        setCompanyName(data.parkingOwner.companyName);
        setOwnerCountry(data.parkingOwner.address.country);
        setOwnerTown(data.parkingOwner.address.town);
        setOwnerStreet(data.parkingOwner.address.streetName);
        setOwnerStreetNumber(data.parkingOwner.address.streetNumber);
    }

    useEffect( () => {
        fetchParking();
    }, [navigation])


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
                    <Text style={styles.categoryText}>Name</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{name}</Text>
                    :
                    <TextInput style={styles.TextInput} value={name} onChangeText={(val) => setName(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Country</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{country}</Text>
                    :
                    <TextInput style={styles.TextInput} value={country} onChangeText={(val) => setCountry(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Street Name</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{streetName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={streetName} onChangeText={(val) => setStreetName(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Street Number</Text>   
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{streetNumber}</Text>
                    :
                    <TextInput style={styles.TextInput} value={streetNumber} onChangeText={(val) => setStreetNumber(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>City</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{town}</Text>
                    :
                    <TextInput style={styles.TextInput} value={town} onChangeText={(val) => setTown(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Number of Spots</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{spotsTotal}</Text>
                    :
                    <TextInput style={styles.TextInput} value={String(spotsTotal)} onChangeText={(val) => setSpotsTotal(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Price per Hour</Text>
                    {isEditingAddress == false ? 
                    <Text style={styles.categoryField}>{pricePerHour}</Text>
                    :
                    <TextInput style={styles.TextInput} value={String(pricePerHour)} onChangeText={(val) => setPPH(val)}></TextInput>}
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
                    <Text style={styles.categoryText}>First Name</Text>  
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{firstName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={firstName} onChangeText={(val) => setFirstName(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Last Name</Text>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{lastName}</Text>
                    :
                    <TextInput style={styles.TextInput} value={lastName} onChangeText={(val) => setLastName(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Country</Text>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{ownerCountry}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerCountry} onChangeText={(val) => setOwnerCountry(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Street Name</Text>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{ownerStreet}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerStreet} onChangeText={(val) => setOwnerStreet(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>Street Number</Text>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{ownerStreetNumber}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerStreetNumber} onChangeText={(val) => setOwnerStreetNumber(val)}></TextInput>}
                </View>
                <View>
                    <Text style={styles.categoryText}>City</Text>
                    {isInvoiceEditing == false ? 
                    <Text style={styles.categoryField}>{ownerTown}</Text>
                    :
                    <TextInput style={styles.TextInput} value={ownerTown} onChangeText={(val) => setOwnerTown(val)}></TextInput>}                 
                </View>
            <View style={{margin: 30}}/>
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

    titleText: {
        fontSize: 20,
        margin: 10,
        fontFamily: ""
    },

    editSaveFunc: {
        fontSize: 20,
        margin: 10
    },

    categoryText: {
        marginTop: 10,
        marginBottom: 5,
        marginHorizontal: 10,
        fontSize: 20
    },

    categoryField: {
        marginBottom: 10,
        marginHorizontal: 10,
        fontSize: 20
    },

    buttonHolder: 
    {
        flex: 1,
        flexDirection: "row",
        justifyContent: "space-between",
        position: 'absolute',
        marginTop: 20,
        bottom: 10, 
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
