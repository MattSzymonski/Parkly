import { StatusBar } from "expo-status-bar";
import React, { useState, useRef } from "react";
import {
  StyleSheet,
  Text,
  View,
  Image,
  TextInput,
  Button,
  TouchableOpacity,
  ScrollView,
} from "react-native";

import axios from 'axios';

const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

export default function AddEntry({navigation, route}) {

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

    const [adding, setAdding] = useState(false)
    const [err, setErr] = useState("");

    const options = {
        headers: {'security-token': route.params.token.current}
    }

    const createParking = () => {
        axios.post(`${api_url}/p/parkings`, {
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
        options).then(response => navigation.navigate('List', {token: route.params.token, user: route.params.username})).catch(err => setErr(err))
    }

return (<ScrollView style={styles.container} contentContainerStyle={{alignItems: "center"}}>
    <Text style={{fontSize: 20, marginBottom: 15, paddingTop: 20}}> Adding new entry: </Text>
    <Text style={{marginBottom: 8}}> Name: </Text>
    <View style={styles.inputView}>
        
        <TextInput
                style={styles.TextInput}
                placeholder="name"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setName(val)}
        />
    </View>
    <Text style={{fontSize: 16, marginBottom: 15, paddingTop: 20}}> Address </Text>
    <Text style={{marginBottom: 8}}>Country: </Text>
    <View style={styles.inputView}>
        
        <TextInput
                style={styles.TextInput}
                placeholder="country"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setCountry(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Town: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="town"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setTown(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Street: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="street"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setStreet(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Street number: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="street number"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setStreetNumber(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Total spots: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="number of spots"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setSpotsTotal(parseInt(val))}
        />
    </View>
    <Text style={{marginBottom: 8}}>Price per hour: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="price per hour"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setPricePerHour(parseInt(val))}
        />
    </View>
    <Text style={{fontSize: 16, marginBottom: 15, paddingTop: 20}}> Parking Owner </Text>
    <Text style={{marginBottom: 8}}>First name: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="first name"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setFirstName(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Last name: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="last name"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setLasttName(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Company: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="company name"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setCompanyName(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Country: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="country"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setOwnerCountry(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Town: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="town"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setOwnerTown(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Street: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="street"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setOwnerStreet(val)}
        />
    </View>
    <Text style={{marginBottom: 8}}>Street number: </Text>
    <View style={styles.inputView}>
        <TextInput
                style={styles.TextInput}
                placeholder="street number"
                placeholderTextColor="#003f5c"
                onChangeText={(val) => setOwnerStreetNumber(val)}
        />
    </View>

    <TouchableOpacity style={styles.loginBtn} onPress={createParking}>
        <Text style={styles.loginText}>Add</Text>
      </TouchableOpacity>

    {/* <View>
        {err.request.map(element => <Text> {element} </Text>)}
    </View> */}

    </ScrollView>
)
  
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: "#fff",
      //alignItems: "center",
      
      //justifyContent: "center",
    },
  
    welcomeContainer: {
        justifyContent: "flex-start",
        alignSelf: "center",
        
    },
  
    welcomeText: {
        //fontFamily: "Open-Sans",
        fontWeight: "bold",
        fontSize: 36,
        marginBottom: 5,
        //alignSelf: "left",
    },
  
    welcomeSubtext: {
        fontWeight: "bold",
        fontSize: 30,
        //marginBottom: 55,
    },
  
    welcomeSubtext2: {
      fontWeight: "bold",
      fontSize: 30,
      marginBottom: 55,
  },
  
  welcomeLogin: {
      fontSize: 20,
      marginBottom: 10,
  },
  
    image: {
      marginBottom: 40,
    },
  
    inputView: {
      backgroundColor: "#ffd300",
      borderRadius: 20,
      //width: "70%",
      width: 240,
      height: 45,
      marginBottom: 20,
      
      //alignItems: "center",
    },
  
    TextInput: {
      height: 50,
      flex: 1,
      padding: 10,
      marginLeft: 10,
      opacity: 0.6,
    },
  
    forgot_button: {
      height: 30,
      //marginBottom: 5,
    },
  
    register_button: {
        height: 30,
        //marginBottom: 10,
    },
  
    loginBtn: {
      //width: "80%",
      width: 150,
      borderRadius: 20,
      height: 50,
      alignItems: "center",
      justifyContent: "center",
      marginTop: 20,
      marginBottom: 40,
      backgroundColor: "#ffd300",
    },

    loginText: {
        fontWeight: "bold",
        fontSize: 16,
    },
  });