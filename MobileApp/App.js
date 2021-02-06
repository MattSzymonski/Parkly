import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import LoginPage from './components/loginPage';
import ListPage from './components/listPage';
import { createStackNavigator } from '@react-navigation/stack'
import { NavigationContainer, StackActions} from '@react-navigation/native'

const Stack = createStackNavigator();

export default function App() {
  return ( <NavigationContainer style={styles.container}>
    
      {/* <LoginPage>
      
        </LoginPage> */}
        {/* <ListPage>
          </ListPage> */}

    
      <Stack.Navigator initialRouteName="Login">
        <Stack.Screen name = "Login" component={LoginPage} />
        <Stack.Screen name = "List" component={ListPage} />
      </Stack.Navigator>
    </NavigationContainer>


  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
