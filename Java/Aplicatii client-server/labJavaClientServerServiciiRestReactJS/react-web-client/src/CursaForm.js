import React from "react";
import './CursaApp.css'

class CursaForm extends React.Component{
    constructor(props){
        super(props);
        this.state = {id: '', nrPersoane: '', capacitateMotor: ''};
    }

    handleIDChange=(event) =>{
        this.setState({id: event.target.value});
    };

    handleNrPersoaneChange=(event) =>{
        this.setState({nrPersoane: event.target.value});
    };

    handleCapMotorChange=(event) =>{
        this.setState({capacitateMotor: event.target.value});
    };

    handleSubmit=(event) =>{
        var race = {
            id: this.state.id,
            nrPersoane: parseInt(this.state.nrPersoane, 10),
            capacitateMotor: parseInt(this.state.capacitateMotor, 10)
        };
        console.log('A race was submitted: ');
        console.log(race);

        this.props.addFunction(race);
        event.preventDefault();
    };

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    ID:
                </label>
                    <input type="text" value={this.state.id} onChange={this.handleIDChange} />
                <br/>
                <label>
                    Nr persoane:
                </label>
                    <input type="text" value={this.state.nrPersoane} onChange={this.handleNrPersoaneChange} />
                <br/>
                <label>
                    Cap motor:
                </label>
                    <input type="text" value={this.state.capacitateMotor} onChange={this.handleCapMotorChange} />
                <br/><br/>

                <input id="submitInput" type="submit" value="Submit"/>
            </form>
        );
    }
}

export default CursaForm;