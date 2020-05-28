import React from "react";
import './CursaApp.css'
import CursaForm from "./CursaForm";
import CursaTable from "./CursaTable";
import {GetRaces, DeleteRace, AddRace} from "./utils/rest-calls";

class CursaApp extends React.Component{
    constructor(props){
        super(props);
        this.state={races:[{"id":"1", "nrPersoane":0, "capacitateMotor":125}],
            deleteFunction:this.deleteFunction.bind(this),
            addFunction:this.addFunction.bind(this),
        };
        console.log('CursaApp constructor');
    }

    addFunction(race){
        console.log('inside addFunction ' + race);
        AddRace(race)
            .then(result => GetRaces())
            .then(races => this.setState({races}))
            .catch(error => console.log('error add ', error));
    }

    deleteFunction(race){
        console.log('inside deleteFunction ' + race);
        DeleteRace(race)
            .then(result => GetRaces())
            .then(races => this.setState({races}))
            .catch(error => console.log('error delete ', error));
    }

    componentDidMount() {
        console.log('inside componentDidMount');
        GetRaces()
            .then(races => this.setState({races}));
    }

    render(){
        return(
            <div className="CursaApp">
                <h1>Race Management</h1>
                <CursaForm addFunction={this.state.addFunction} />
                <br/>
                <CursaTable races={this.state.races} deleteFunction={this.state.deleteFunction} />
            </div>
        );
    }
}

export default CursaApp;