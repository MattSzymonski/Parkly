import React from 'react'
import { View, StyleSheet, Text, StatusBar, Image } from 'react-native';

const listElement = ( {navigation, route} ) => {

    const country = route.params;

    return (
        <View style={styles.container}>
            <Image
            style={styles.flag}
            source={{
              uri: `https://www.countryflags.io/${country.alpha2Code}/flat/64.png`,
            }}
            />
            <Text style={styles.info}> Capital: {country.capital} </Text> 
            <Text style={styles.info}> Alpha2Code: {country.alpha2Code} </Text>  
            <Text style={styles.info}> Population: {country.population} </Text>   
            <Text style={styles.info}> Area: {country.area} km^2 </Text>   
            <Text style={styles.info}> Region: {country.region} </Text>
            <Text style={styles.info}> Subregion: {country.subregion} </Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      marginTop: StatusBar.currentHeight || 0,
    },
    item: {
      backgroundColor: '#00bfff',
      padding: 20,
      marginVertical: 8,
      marginHorizontal: 16,
    },
    title: {
      fontSize: 24,
    },
    flag: {
      height: 128,
      width: 128, 
      alignSelf: 'center'
    }, 
    info: {
        textAlign: 'center',
        fontSize: 18
    }
  });

export default listElement;