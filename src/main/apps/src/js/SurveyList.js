import React, {Component} from 'react';
import SurveyItem from './SurveyItem';
import Box from '@material-ui/core/Box';
import {a11yProps} from "./Util";

export default class SurveyList extends Component {
    constructor(...props) {
        super(...props);
        this.state = {
            surveys : []
        };
    }

    componentDidMount() {
        fetch('/api/surveys')
            .then(response => response.json())
            .then(json => {
                this.setState({
                    surveys : json || []
                });
            });
    };

    render() {
        let idx = 0;
        return (
            <Box p={3}>
                { this.state.surveys.map(survey => <SurveyItem {...a11yProps('survey-item')(idx++)} {...survey}/>) }
            </Box>
        );
    }
}
