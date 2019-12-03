import React, {Component} from 'react';
import Box from '@material-ui/core/Box';
import {a11yProps} from "./Util";
import SurveyResItem from "./SurveyResItem";

export default class SurveyResList extends Component {
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
                { this.state.surveys.map(survey => <SurveyResItem {...a11yProps('survey-res-item')(idx++)} {...survey}/>) }
            </Box>
        );
    }
}
