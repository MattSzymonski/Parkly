// import React from 'react';
// import { SafeAreaView, View, FlatList, StyleSheet, Text, StatusBar, Image } from 'react-native';

// const Details = ({ route, navigation }: any) => {
//     return ( 
//       <SafeAreaView style={styles.container}>
//         <Text> {route.name} </Text>
//         <Image
//               style={styles.flag}
//               source={{
//                 uri: `https://www.countryflags.io/${route.alpha2Code}/flat/64.png`,
//               }}
//             />
//       <View styles={styles.item}>
//         <Text > Capital: {route.capital} </Text> 
//         <Text > Alpha2Code: {route.alpha2Code} </Text>  
//         <Text > Population: {route.population} </Text>   
//         <Text > Area: {route.area} km^2 </Text>   
//         <Text> Region: {route.region} </Text>
//         <Text> Subregion: {route.subregion} </Text>
//         </View>
//         </SafeAreaView>
//     );
//   }

//   const styles = StyleSheet.create({
//     container: {
//       flex: 1,
//       marginTop: 20,
//     },
//     item: {
      
//       backgroundColor: '#00bfff',
//       padding: 5,
//       marginVertical: 8,
//       marginHorizontal: 16,
//     },
//     title: {
//       fontSize: 24,
//     },
//     flag: {
//       height: 64,
//       width: 64
//     }
//   });

//   export default Details;