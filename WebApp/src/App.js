import AdminPanel from './Components/AdminPanel';
import {BrowserRouter as Router, Switch,Route} from "react-router-dom";
import NewPark from './Components/NewPark';
import ListingDetails from './Components/ListingDetails';
import Login from './Components/Login';
import Parker from './Components/ParkerDetails';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Login/>
        </Route>
        <Route exact path="/home">
          <AdminPanel/>
        </Route>
        <Route exact path="/listingdetails">
          <ListingDetails/>
        </Route>
        <Route exact path="/newpark">
          <NewPark/>
        </Route>
        <Route exact path="/parker">
          <Parker/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
