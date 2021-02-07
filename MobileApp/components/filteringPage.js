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

const FilteringPage = ({navigation, route}) => {

    return (
        <ScrollView style={styles.container} contentContainerStyle={{alignItems: "center"}}>
            
        </ScrollView>
    );  
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff",
      },

      TextInput: {
        height: 50,
        flex: 1,
        padding: 10,
        marginLeft: 10,
        opacity: 0.6,
      },

      inputView: {
        backgroundColor: "#ffd300",
        borderRadius: 20,
        width: 240,
        height: 45,
        marginBottom: 20,
      },

      standardText: {
          marginBottom: 8
      }
})

export default FilteringPage;