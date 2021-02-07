import React, {useEffect, useState, useRef} from 'react'
import { SafeAreaView, View, FlatList, StyleSheet, Text, StatusBar, Image, Animated } from 'react-native';
import { TouchableOpacity } from 'react-native';
import { SearchBar } from 'react-native-elements';

function listOfCountries({navigation})  {
    const [isLoading, setLoading] = useState(true);
    const [countries, setCountries] = useState([]);
    const [search, setSearch] = useState("");
    const [refreshing, setRefreshing] = useState(false);
    const [isSearching, setSearching] = useState(false);
  
    useEffect(() => {
      fetch('https://restcountries.eu/rest/v2/all')
        .then((response) => response.json())
        .then((json) => setCountries(json))
        .catch((error) => console.error(error))
        .finally(() => (setLoading(false), fadeIn()));
    }, []);

    const renderListItem = ( {item} ) => (

        <View style={styles.item}>   
                   <Animated.View
                       style={[
                        {
                          opacity: fadeAnim
                         }
                          ]}>
          <TouchableOpacity onPress={() => navigation.push('Country', item)}>
            <Text style={styles.title}>{item.name}</Text>
            <Image
                style={styles.flag}
                source={{
                uri: `https://www.countryflags.io/${item.alpha2Code}/flat/64.png`,
            }}
          />
          </TouchableOpacity> 
            </Animated.View>
        </View>
      );

      const setCountriesC = (countries) => {
        setCountries(countries);
        fadeOut();
        fadeIn();
      }

      const searchFor = (search) => {
        setSearch(search);
        
        if (search.length >= 3) {
          setSearching(true);
          fetch(`https://restcountries.eu/rest/v2/name/${search}`)
          .then((response) => response.json())
          .then((json) => setCountriesC(json))
          .catch((error) => console.error(error))
          .finally(() => {setLoading(false); setSearching(false); fadeOut(); fadeIn()});
          
        }
    
        if (search.length == 0) {
          setSearching(true);
          fetch(`https://restcountries.eu/rest/v2/all`)
          .then((response) => response.json())
          .then((json) => setCountries(json))
          .catch((error) => console.error(error))
          .finally(() => {setLoading(false); setSearching(false); fadeIn()});
          
        }
      }
    
      const handleRefresh = () => {
        setRefreshing(true);
        fetch('https://restcountries.eu/rest/v2/all')
        .then((response) => response.json())
        .then((json) => setCountries(json))
        .catch((error) => console.error(error))
        .finally(() => (setLoading(false), fadeIn()));
        setRefreshing(false);
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
          height: 64,
          width: 64
        },
        input: {
          borderStyle: 'solid',
          borderWidth: 3,
          margin: 10
        },
        info: {
            textAlign: 'center',
            fontSize: 18
        }
      });

  const fadeAnim = useRef(new Animated.Value(0)).current;

  const fadeIn = () => {
    // Will change fadeAnim value to 1 in 5 seconds
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 2500
    }).start();
  };

  const fadeOut = () => {
    // Will change fadeAnim value to 0 in 5 seconds
    Animated.timing(fadeAnim, {
      toValue: 0,
      duration: 2500
    }).start();
  };
    
    return (
     <SafeAreaView style={styles.container}>
      { isLoading ? <Text style={styles.title}>Loading...</Text> :
             
          <View>


          <Text>Found {countries.length} countries</Text>
          <SearchBar  placeholder="Find countries ..."
          onChangeText={searchFor}
          value={search}
           />
             {
               countries.length > 0 ?
               <Text style={styles.title}> Found {countries.length} countries! </Text>
               :
               <Text style={styles.title}> Found 0 countries! </Text>
             }
             {
               isSearching ? 
               <Text style={styles.title}> Searching ... </Text>
               :
               null
             }
 
             { countries.length > 0 ?  
                    <Animated.View
                       style={[
                        {
                          opacity: fadeAnim
                         }
                          ]}>
                       <FlatList
                       data={countries.slice(0, countries.length)}
                       renderItem={item => renderListItem(item)}
                       keyExtractor={item => item.name}
                       refreshing={refreshing}
                       onRefresh={handleRefresh}

                     />
                    </Animated.View>
                     :
                     <Text> No countries found!</Text>
                     }
         
          </View>
      }
      </SafeAreaView>
      
      );
}

export default listOfCountries;



