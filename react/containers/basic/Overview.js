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

    getColor(bigVal, thisVal) {
        console.log(bigVal, thisVal);
        const val = Math.max(0, thisVal / bigVal * (this.colorMaxVal - this.colorMinVal) + this.colorMinVal);
        return `hsl(${val}, 68%, 80%)`
    }

    render() {

        const data = this.props.overview.overviewData;

        let biggestVals = [];

        biggestVals.push(Math.max.apply(Math, data.map(d => d.values[0].relativeDiffRaw)));
        biggestVals.push(Math.max.apply(Math, data.map(d => d.values[1].relativeDiffRaw)));
        biggestVals.push(Math.max.apply(Math, data.map(d => d.values[2].relativeDiffRaw)));
        biggestVals.push(Math.max.apply(Math, data.map(d => d.values[3].relativeDiffRaw)));
        biggestVals.push(Math.max.apply(Math, data.map(d => d.values[4].relativeDiffRaw)));


        const tableRows = data.map(row => {
            const stock = row.stock;
            const vals = row.values.map((val, index) => {
                const color = this.getColor(biggestVals[index], val.relativeDiffRaw);
                console.log(val);
                return (
                    <Table.Cell style={{backgroundColor: color}}>
                        {val.relativeDiff} %<br/>
                        {/*<i>{val.absoluteDiff}</i>*/}
                    </Table.Cell>
                )
            });

            return (
                <Table.Row>
                    <Table.Cell>
                        <Statistic label={stock.type}/>
                    </Table.Cell>
                    <Table.Cell>
                        <b>{stock.name}</b><br/>
                        <i>{stock.isin}</i>
                    </Table.Cell>
                    {vals}
                </Table.Row>
            )
        });

        return (
            <div>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>WP-Art</Table.HeaderCell>
                            <Table.HeaderCell>WP</Table.HeaderCell>
                            <Table.HeaderCell>Sechs Monate</Table.HeaderCell>
                            <Table.HeaderCell>Drei Monate</Table.HeaderCell>
                            <Table.HeaderCell>Ein Monat</Table.HeaderCell>
                            <Table.HeaderCell>Eine Woche</Table.HeaderCell>
                            <Table.HeaderCell>Seit Gestern</Table.HeaderCell>
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