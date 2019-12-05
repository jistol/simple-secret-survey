import React from "react";
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import SurveyApp from "./js/SurveyApp";
import SurveyList from "./js/SurveyList";
import SurveyResList from "./js/SurveyResList";

const SurveyRouter = () => {
    return (
        <Router>
            <Switch>
                <Route exact path="/survey" component={ SurveyList }/>
                <Route exact path="/survey/admin" component={ SurveyApp }/>
                <Route exact path="/survey/result" component={ SurveyResList }/>
                <Redirect to='/survey' />
            </Switch>
        </Router>
    );
};

export default SurveyRouter;