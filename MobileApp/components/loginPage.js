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
  KeyboardAvoidingView,
} from "react-native";

import axios from 'axios';

const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

export default function LoginPage({navigation}) {
  const [username, setUsername] = useState("ParklyAppTest");
  const [password, setPassword] = useState("root");
  const [fetching, setFetching] = useState(false);
  //const [token, setToken] = useState("");
  const [err, setErr] = useState("");
  const username2 = useRef("ParklyAppTest");
  const token = useRef("");

  const userLogin = () => {
    setErr("");
    setFetching(true);
    axios.post(`${api_url}/p/login`, {
      login: username,
      password: password
    }).then((response) =>  {setFetching(false); token.current = response.data; navigation.navigate('List', {token: token, user: username2})}).catch((err) => {setErr(err), setFetching(false)})
  }


  return (
    <KeyboardAvoidingView behavior="padding" style={styles.container}>
      <View style={styles.welcomeContainer}>
            <Text style={styles.welcomeText}>
                PARKLY.
            </Text>
      </View>
      <View style={styles.welcomeContainer}>
      <Text style={styles.welcomeSubtext}>
                Parking made
        </Text>
      </View>
      <View style={styles.welcomeContainer}>
      <Text style={styles.welcomeSubtext2}>
                SIMPLE.
        </Text>
      </View>
      

    <View style={styles.welcomeContainer}>
        <Text style={styles.welcomeLogin}> Login </Text>
    </View>
      <StatusBar style="auto" />
      <View style={styles.inputView}>
        <TextInput
          style={styles.TextInput}
          placeholder="username"
          placeholderTextColor="#003f5c"
          secureTextEntry={false}
          onChangeText={(email) => {setUsername(email); username2.current=email}}
        />
      </View>

      <View style={styles.inputView}>
        <TextInput
          style={styles.TextInput}
          placeholder="password"
          placeholderTextColor="#003f5c"
          secureTextEntry={true}
          onChangeText={(password) => setPassword(password)}
        />
      </View>

      <TouchableOpacity style={styles.loginBtn} onPress={userLogin}>
        <Text style={styles.loginText}>LOGIN</Text>
      </TouchableOpacity>

      { fetching ? 
        <View>
          <Text> Please wait ... </Text>
        </View>
      :
      null
      }

     {
       err === "" ?
       null
       :
       <View>
       <Text style={{paddingTop: 35, fontWeight: 'bold'}}>Failed to login!</Text>
       <Text style={{fontWeight: 'bold'}}>Please check your credentials!</Text>
       </View>
     }

    </KeyboardAvoidingView>

  );
  
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
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
    marginTop: 40,
    backgroundColor: "#ffd300",
  },
});
