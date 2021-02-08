import React, { useCallback, useEffect, useState } from 'react';
import { SafeAreaView, View, FlatList, StyleSheet, Text, StatusBar, Image, Dimensions, TextInput, TouchableOpacity } from 'react-native';
import { SearchBar } from 'react-native-elements';
import { Button, DataTable } from 'react-native-paper';
import axios from 'axios';
import { useFocusEffect } from '@react-navigation/native';
import Collapsible from 'react-native-collapsible';
import { ScrollView } from 'react-native-gesture-handler';

const api_url = "http://parkly-env.eba-u2qumtf7.us-east-2.elasticbeanstalk.com";

const ListPage = ({navigation, route}) => {

    const [parkings, setParkings] = useState([[]]);
    const [loading ,setLoading] = useState(true);
    const [totalPages, setPages] = useState(0);
    const [currentPage, setPage] = useState(0);

    const [isFiltering, setFiltering] = useState(false);
    const [filteredName, setFilteredName] = useState("");
    const [filteredStreetName, setFilteredStreetName] = useState("");
    const [filteredTown, setFilteredTown] = useState("");
    const [filteredCountry, setFilteredCountry] = useState("");
    const [filteredPricePerHous, setMaxPricePerHour] = useState("");

    const [alreadyFiltered, setAlreadyFiltered] = useState(false);
    const [filter, setFilter] = useState("");
    const [finishedFiltering, setFinishedFiltering] = useState(false);

    const testStringFilter = (field, value) => {
        if(value.length > 0)
            if(alreadyFiltered == false) {
                setFilter(`${field}=${value}`);
                setAlreadyFiltered(true);
            }
            else    
                setFilter(filter + `&${field}=${value}`);
    }

    const updateFilter = () => {
        setFilter("");
        testStringFilter('town', filteredTown);
        testStringFilter('streetName', filteredStreetName);
        testStringFilter('country', filteredCountry);
        testStringFilter('name', filteredName);
        testStringFilter('maximumPricePerHour', filteredPricePerHous);
        setAlreadyFiltered(false);
        setFinishedFiltering(true);
    }

    const handleFilterApplying = () => {
        setFiltering(false); 
        dataFetch();
        pageChange(0);
    }

    const ite = route.params;

    const options = {
        headers: {'security-token': ite.token.current}
    }

    const dataFetch = () => {
        if(filter.length == 0)
            axios.get(api_url + '/p/parkings', options).then((response) =>  {setParkings(response.data); setPages(response.data.totalPages)}).finally(() => setLoading(false));
        else
            axios.get(api_url + '/p/parkings?' + filter, options).then((response) =>  {setParkings(response.data); setPages(response.data.totalPages)}).finally(() => setLoading(false));
    }

    useEffect( () => {
        navigation.addListener('focus', () => {
            setLoading(true);
            dataFetch();
            setPage(0);
        })
    }, [navigation]);

    useEffect( () => {
        handleFilterApplying();
    }, [filter])

    const pageChange = (cp) => {
        setPage(cp);
        setLoading(true);
        if(filter.length == 0)
            axios.get(`${api_url}/p/parkings?page=${cp}`, options).then( (response) => {setParkings(response.data); setPages(response.data.totalPages)}).finally( () => {setLoading(false);})
        else
            axios.get(`${api_url}/p/parkings?page=${0}&` + filter, options).then( (response) => {setParkings(response.data); setPages(response.data.totalPages)}).finally( () => {setLoading(false);})
    }

  return (
    <ScrollView style={styles.container}>
        <View style={styles.bannerContainer}>
            <View style={styles.banner}>
                <Text style={styles.welcomeSubtext}> PARKLY.</Text>
                <Text> Admin </Text>
            </View>
            <View style={styles.welcomeUser}>
                <Text style={{fontSize: 18}}><Text> {route.params.user.current}</Text> </Text>
                
            </View>
        </View>

        <View style={styles.filterContainer}>
        <TouchableOpacity style={styles.entryBtn} onPress={() => {const x = (isFiltering + 1) % 2; setFiltering(x);}}>
                    <Text>Filter</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.filterBtn} onPress={() => navigation.navigate('Add', {token: ite.token, username:ite.user})}>
                    <Text>Add new entry</Text>
        </TouchableOpacity>

        </View>

        <View style={styles.filterContainer}>   

        </View>

        <View>
            
        </View>
        <SafeAreaView>
                <Collapsible collapsed={!isFiltering}>
                    <ScrollView>
                        <Text style={styles.categoryText}>Name</Text>
                        <TextInput style={styles.TextInput} value={filteredName} onChangeText={(val) => setFilteredName(val)} />
                        <Text style={styles.categoryText}>Country</Text>
                        <TextInput style={styles.TextInput} value={filteredCountry} onChangeText={(val) => setFilteredCountry(val)} />
                        <Text style={styles.categoryText}>City</Text>
                        <TextInput style={styles.TextInput} value={filteredTown} onChangeText={(val) => setFilteredTown(val)} />
                        <Text style={styles.categoryText}>Street Name</Text>
                        <TextInput style={styles.TextInput} value={filteredStreetName} onChangeText={(val) => setFilteredStreetName(val)} />
                        <Text style={styles.categoryText}>Maximum Price Per Hour</Text>
                        <TextInput style={styles.TextInput} value={filteredPricePerHous} onChangeText={(val) => setMaxPricePerHour(val)} />
                        <TouchableOpacity style={styles.applyFilterBtn}  onPress={() => {updateFilter()}}>
                        <Text>Apply Filter</Text>
                        </TouchableOpacity>
                    </ScrollView>
                </Collapsible>
        </SafeAreaView>
        <View>       
            <DataTable>          
                <DataTable.Header>
                    <DataTable.Title> Action </DataTable.Title>
                    <DataTable.Title> Name </DataTable.Title>
                    {/* <DataTable.Title> Country </DataTable.Title> */}
                    <DataTable.Title> Town </DataTable.Title>
                    <DataTable.Title> Spots </DataTable.Title>
                </DataTable.Header>
            { loading ? 
            <Text> Loading ... </Text>
            :
            <SafeAreaView>
            {
                parkings.items.map(element => (
                
                <DataTable.Row style={{height: 80}}>
                    <DataTable.Cell> <TouchableOpacity onPress={() => navigation.navigate('Details', {token: ite.token, username:ite.user, item:element})} style={styles.mgmtBtn}><Text>Manage</Text>
                    </TouchableOpacity>
                    </DataTable.Cell>
                    <DataTable.Cell> {element.name} </DataTable.Cell>
                    {/* <DataTable.Cell> {element.address.country}</DataTable.Cell> */}
                    <DataTable.Cell> {element.address.town}</DataTable.Cell>
                    <DataTable.Cell> {element.spotsTaken}/{element.spotsTotal}</DataTable.Cell>
                    
                </DataTable.Row>
                
              ))
            }
            </SafeAreaView>
            }
           
                <DataTable.Pagination
                page={currentPage}
                numberOfPages={totalPages}
                label={`${currentPage + 1} of ${totalPages}`}
                onPageChange={(page) => pageChange(page)}
                />
                <View style={{flexDirection: "row", justifyContent: "center", paddingTop: 20}}>
                <TouchableOpacity onPress={() => pageChange(0)}>
                <Text style={{alignSelf: "flex-end", paddingRight: 10, fontSize: 12}}>First page</Text>
                </TouchableOpacity>
                
                <Text style={{alignSelf: "flex-end", paddingRight: 10, fontSize: 12}}> | </Text>
                <TouchableOpacity onPress={() => pageChange(totalPages-1)}>
                <Text style={{alignSelf: "flex-end", paddingRight: 10, fontSize: 12}}>Last page</Text>
                </TouchableOpacity>
                
                </View>
         </DataTable>
         
    </View>
</ScrollView>

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

      mgmtBtn: {
        
        borderRadius: 10,
        height: 35,
        width: 70,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#ffd300",
      },

      applyFilterBtn: {
        margin: 20,
        borderRadius: 10,
        height: 50,
        alignItems: "center",
        display: "flex",
        flex: 1,
        justifyContent: "center",
        alignSelf: "center",
        width: Dimensions.get("window").width - 200,
        backgroundColor: "#ffd300",
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

  inputView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },

  TextInput: {
    height: 50,
    paddingLeft: 30,
    backgroundColor: "#f8f8ff",
    borderRadius: 20,
  },

  TextInputAdd: {
    flex: 1,
    height: 30,
    width: Dimensions.get("window").width-140,
    paddingLeft: 15,
    backgroundColor: "#f8f8ff",
    borderRadius: 10,
    marginLeft: 35,
    marginRight: 35,
    marginTop: 10,
  },

  categoryText: {
    marginTop: 10,
    marginBottom: 5,
    marginHorizontal: 10,
    },

  });

export default ListPage;
