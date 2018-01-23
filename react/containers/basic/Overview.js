import React from "react";
import {connect} from "react-redux";
import {getOverviewData} from "../../actions/overviewActions";
import {Statistic, Table} from "semantic-ui-react";

@connect((store) => {
    return {
        overview: store.overview
    }
})
export default class Overview extends React.Component {

    componentWillMount() {
        this.props.dispatch(getOverviewData());

        this.colorMaxVal = 115;
        this.colorMinVal = 0;
    }

    getColor(bigVal, lowVal, thisVal) {
        if (thisVal === 0)
            return "white";
        const val = Math.max(-2, (thisVal - lowVal) / (bigVal - lowVal) * (this.colorMaxVal - this.colorMinVal) + this.colorMinVal);
        return `hsl(${val}, 68%, 80%)`
    }

    render() {

        const data = this.props.overview.overviewData;

        let biggestVals = [];
        let smallestVals = [];

        [0, 1, 2, 3, 4].map((i) => {
            biggestVals.push(Math.max.apply(Math, data.map(d => d.values[i].relativeDiffRaw)));
            smallestVals.push(Math.min.apply(Math, data.map(d => d.values[i].relativeDiffRaw)));
        });


        const highestmomentum = Math.max.apply(Math, data.map(d => Math.round(d.momentum * 100) / 100));
        const lowestmomentum = Math.min.apply(Math, data.map(d => Math.round(d.momentum * 100) / 100));


        const tableRows = data.map(row => {
            const stock = row.stock;
            const vals = row.values.map((val, index) => {
                const color = this.getColor(biggestVals[index], smallestVals[index], val.relativeDiffRaw);
                const outputValue = val.relativeDiffRaw !== 0 ? val.relativeDiff + " %" : "-";
                return (
                    <Table.Cell style={{backgroundColor: color}}>
                        {outputValue}<br/>
                        {/*<i>{val.absoluteDiff}</i>*/}
                    </Table.Cell>
                )
            });

            const momentum = Math.round(row.momentum * 100) / 100;
            const momentumColor = this.getColor(highestmomentum, lowestmomentum, momentum);

            return (
                <Table.Row>
                    <Table.Cell>
                        <Statistic label={stock.type}/>
                    </Table.Cell>
                    <Table.Cell>
                        <i>{stock.isin}</i>
                    </Table.Cell>
                    <Table.Cell>
                        <b>{stock.name}</b>
                    </Table.Cell>
                    {vals}
                    <Table.Cell style={{backgroundColor: momentumColor}}>
                        {momentum}
                    </Table.Cell>
                </Table.Row>
            )
        });

        return (
            <div>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>WP-Art</Table.HeaderCell>
                            <Table.HeaderCell>ISIN</Table.HeaderCell>
                            <Table.HeaderCell>WP</Table.HeaderCell>
                            <Table.HeaderCell>Sechs Monate</Table.HeaderCell>
                            <Table.HeaderCell>Drei Monate</Table.HeaderCell>
                            <Table.HeaderCell>Ein Monat</Table.HeaderCell>
                            <Table.HeaderCell>Eine Woche</Table.HeaderCell>
                            <Table.HeaderCell>Seit Gestern</Table.HeaderCell>
                            <Table.HeaderCell>Momentum</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {tableRows}
                    </Table.Body>
                </Table>
            </div>
        )
    }

}