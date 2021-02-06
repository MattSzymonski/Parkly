import React, { useEffect, useState } from 'react';
import { SafeAreaView, View, FlatList, StyleSheet, Text, StatusBar, Image, Dimensions, TextInput, TouchableOpacity } from 'react-native';
import { SearchBar } from 'react-native-elements';
import { DataTable } from 'react-native-paper';
import axios from 'axios';

//   const renderListItem = ({ item }) => (
//     <View>
//         <DataTable.Row>
//             <DataTable.Cell> 1 </DataTable.Cell>
//             <DataTable.Cell> test </DataTable.Cell>
//         </DataTable.Row>
//     </View>
//   );
const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

const ListPage = ({navigation, route}) => {

    const [parkings, setParkings] = useState([[]]);
    const [loading ,setLoading] = useState(true);
    
    // useEffect(() => {
    //     fetch(`${api_url}/p/parkings`)
    //       .then((response) => response.json())
    //       .then((json) => setParkings(json))
    //       .catch((error) => console.error(error))
    //       .finally(() => (setLoading(false)));
    //   }, []);

    const ite = route.params;

    const options = {
        headers: {'security-token': ite.token.current}
    }

    useEffect( () => {
        axios.get(`${api_url}/p/parkings`, options).then((response) =>  {setParkings(response.data)}).finally(() => setLoading(false))
    }, []);

    const renderRow = (item) => {
        return(
        // <DataTable.Row>
        //     <DataTable.Cell> {item.id} </DataTable.Cell>
        //     <DataTable.Cell>  {item.name} </DataTable.Cell>
        //     <DataTable.Cell>  {item.name} </DataTable.Cell>
        //     <DataTable.Cell> {item.name} </DataTable.Cell>
        //     <DataTable.Cell> {item.name} </DataTable.Cell>
           
        // </DataTable.Row>
    <Text>{item.name}</Text> 
        )
      
    }

  return (
//     <StickyHeaderFooterScrollView
//     renderStickyHeader={() => (
//       <View style={{height: 120, backgroundColor: "#ccc"}}>
//         <Text>{`PARKLY.`}</Text>
//         <Text> Parking made</Text>
//         <Text> SIMPLE. </Text>
//       </View>
//     )}
//     >
//     <View style={styles.container}>
//       <Text>LIST LIST LIST</Text>
//     </View>
//   </StickyHeaderFooterScrollView>
    <View style={styles.container}>
        <View style={styles.bannerContainer}>
            <View style={styles.banner}>
                <Text style={styles.welcomeSubtext}> PARKLY.</Text>
                <Text> Admin </Text>
            </View>
            <View style={styles.welcomeUser}>
                <Text style={{fontSize: 18}}><Text> {ite.user.current}</Text> </Text>
                
            </View>
            
            {/* <Text style={{fontSize: 30}}> Parking made </Text>
            <Text style={{fontSize: 30}}> SIMPLE.</Text> */}
        </View>
        <View style={styles.bannerContainer}>
            <View style={styles.searchContainer}>
                <TextInput style={styles.TextInput} placeholder="Search for objects..." placeholderTextColor="#000"></TextInput>

            </View> 
            <TouchableOpacity style={styles.searchBtn}>
                    <Text>Search</Text>
            </TouchableOpacity>
        </View>

        <View style={styles.filterContainer}>
        <TouchableOpacity style={styles.entryBtn}>
                    <Text>Filter</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.filterBtn}>
                    <Text>Add new entry</Text>
        </TouchableOpacity>
        </View>

        <View>
            
        </View>
        <View>
            <DataTable style={styles.tableContainer}>
                <DataTable.Header>
                    <DataTable.Title> Action </DataTable.Title>
                    <DataTable.Title> Name </DataTable.Title>
                    <DataTable.Title> Country </DataTable.Title>
                    <DataTable.Title> Town </DataTable.Title>
                    <DataTable.Title> Spots </DataTable.Title>
                </DataTable.Header>
                {/* <DataTable.Row>
                    <DataTable.Cell> Manage </DataTable.Cell>
                    <DataTable.Cell> Test </DataTable.Cell>
                    <DataTable.Cell> Poland </DataTable.Cell>
                    <DataTable.Cell> Warsaw </DataTable.Cell>
                    <DataTable.Cell> 50/50 </DataTable.Cell>
                </DataTable.Row> */}
                {/* <DataTable.Row>
                    <DataTable.Cell>
                    {parkings.items}
                    </DataTable.Cell>
                    <DataTable.Cell>
                    {parkings.length}
                    </DataTable.Cell>
                    <DataTable.Cell>
                    {parkings.length}
                    </DataTable.Cell>
                    <DataTable.Cell>
                    {parkings.length}
                    </DataTable.Cell>
                    <DataTable.Cell>
                    {parkings.length}
                    </DataTable.Cell>
                
                </DataTable.Row> */}
            
            
            { loading ? 
            <Text> Loading ... </Text>
            :
            <SafeAreaView style={{flex:1}}>
            <FlatList
                data={parkings.items}
                keyExtractor={(item,index) => item.id}
                renderItem={(item) => (renderRow(item))}
            />
            </SafeAreaView>
            }
         </DataTable>
         
    </View>
</View>

  );
}

const styles = StyleSheet.create({
    
    container: {
        flex: 1,
        //flexDirection: "column",
        //paddingTop: 50,
    },

    bannerContainer: {
        flexDirection: "row",
        backgroundColor: "#ffd300",
        //paddingLeft: 15,
        paddingTop: 15,
        paddingRight: 15,
    },

    filterContainer: {
        flexDirection: "row",
        backgroundColor: "#ffd300",
        //paddingLeft: 15,
        paddingBottom: 15,
        paddingRight: 15,
        
    },

    searchContainer: {
        width: Dimensions.get("window").width - 100,
        paddingLeft: 35,
        paddingRight: 15,
        paddingBottom: 15,
    },

    searchBtn: {
        //width: "80%",
        //width: 150,
        borderRadius: 10,
        height: 50,
        // width: 150,
        alignItems: "center",
        justifyContent: "center",
        // paddingLeft: 35,
        // paddingRight: 35,
        width: 80,
        backgroundColor: "#FAEBD7",
      },

    banner: {
        //position: "absolute",
        //top: 0,
        flexDirection: "row",
        width: Dimensions.get("window").width/2,
        height: 65,
        borderBottomLeftRadius: 20,
        paddingLeft: 10,
      },

    welcomeContainer: {
        justifyContent: "flex-start",
        alignSelf: "flex-start",
        width: '80%'
    },

    tableContainer: {
        marginLeft: 15,
        marginRight: 15,
    },
  
    welcomeText: {
        //fontFamily: "Open-Sans",
        
        fontSize: 36,
        marginBottom: 5,
        //alignSelf: "left",
    },
  
    welcomeSubtext: {
        fontWeight: "bold",
        fontSize: 30,
        //marginBottom: 55,
    },

    welcomeUser: {
        width: Dimensions.get("window").width/2,
        flexDirection: "row",
        justifyContent: "flex-end",
        borderBottomRightRadius: 20,
        paddingRight: 10,
    },
  
    welcomeSubtext2: {
      fontSize: 30,
  },
  
  filterBtn: {
    borderRadius: 10,
    height: 50,
    // width: 150,
    alignItems: "center",
    justifyContent: "center",
    marginLeft: 35,
    width: Dimensions.get("window").width - 200,
    backgroundColor: "#FAEBD7",
  },

  entryBtn: {
    borderRadius: 10,
    height: 50,
    // width: 150,
    alignItems: "center",
    justifyContent: "center",
    marginLeft: 35,
    width: 110,
    backgroundColor: "#FAEBD7",
  },


  welcomeLogin: {
      fontSize: 20,
      marginBottom: 10,
  },

  TextInput: {
    height: 50,
    paddingLeft: 30,
    backgroundColor: "#f8f8ff",
    borderRadius: 20,
  },

  });

export default ListPage;