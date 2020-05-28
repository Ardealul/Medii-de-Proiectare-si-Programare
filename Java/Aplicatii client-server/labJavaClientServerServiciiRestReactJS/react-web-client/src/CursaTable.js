import React from "react";
import './CursaApp.css'

class CursaRow extends React.Component{

    handleClick=(event)=>{
        console.log('delete button for ' + this.props.race.id);
        this.props.deleteFunction(this.props.race.id);
    };

    render(){
        return(
            <tr>
                <td>{this.props.race.id}</td>
                <td>{this.props.race.nrPersoane}</td>
                <td>{this.props.race.capacitateMotor}</td>
                <td><button onClick={this.handleClick}>Delete</button></td>
            </tr>
        );
    }
}

class CursaTable extends React.Component{
    render(){
        var rows = [];
        var functieStergere = this.props.deleteFunction;
        this.props.races.forEach(function (race) {
            rows.push(<CursaRow race={race} key={race.id} deleteFunction={functieStergere} />);
        });

        return(
            <div className="CursaTable">
                <table className="center">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nr persoane</th>
                        <th>Cap motor</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>
            </div>
        );
    }
}

export default CursaTable;