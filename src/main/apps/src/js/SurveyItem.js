import React, {useState} from 'react';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Dialog from "@material-ui/core/Dialog/Dialog";
import IconButton from "@material-ui/core/IconButton/IconButton";
import Button from "@material-ui/core/Button/Button";
import {makeStyles} from "@material-ui/core";
import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import Toolbar from "@material-ui/core/Toolbar/Toolbar";
import CloseIcon from '@material-ui/icons/Close';
import ScoreGroup from "./ScoreGroup";
import {a11yProps} from "./Util";
import Box from "@material-ui/core/Box/Box";
import AppBar from "@material-ui/core/AppBar/AppBar";

const useStyles = makeStyles(theme => ({
    appBar: {
        position: 'relative',
    },
    title: {
        marginLeft: theme.spacing(2),
        flex: 1,
    },
    paper : {
        padding : theme.spacing(2),
        margin : theme.spacing(2),
        cursor : 'pointer'
    },
    toolbar : {
        margin: '0',
        paddingTop: '10px',
        paddingBottom: '10px',
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        borderBottom: '1px',
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        color: 'white'
    },
    saveBtn : {
        background: 'linear-gradient(45deg, #F06080 50%, #E5703A 50%)',
        border: 0,
        borderRadius: 3,
        boxShadow: '0 3px 5px 2px rgba(255, 155, 155, .3)',
        color: 'white',
        height: 48,
        padding: '0 30px'
    }
}));


export default function SurveyItem(props) {
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const [question, setQuestion] = useState({});
    const [answerItemList, setAnswerItemList] = useState([]);
    const makeAnswerItem = item => {
        return {
            questionItemId : item.questionItemId,
            description : item.description,
            score : -1
        }
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setAnswerItemList([]);
        setOpen(false);
    };

    const handleSave = () => {
        if (!validate()) {
            alert('완료되지 않은 설문이 존재합니다.');
            return;
        }

        fetch(`/api/surveys/${props.surveyId}/answer`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: 'POST',
                body : JSON.stringify({
                    answerItemList: answerItemList
                })
            })
            .then(res => res.json())
            .then(res => {
                if (res.result) {
                    alert('설문이 등록되었습니다.');
                    handleClose();
                } else {
                    throw new Error(res.message);
                }
            })
            .catch(res => {
                alert(`설문 등록중 오류가 발생했습니다. ${res.text()}`);
            });
    };

    const validate = () => {
        return answerItemList && answerItemList.length > 0
            && answerItemList.filter(item => item.score === -1).length < 1;
    };

    const handleEnter = () => {
        fetch(`/api/surveys/${props.surveyId}/question`)
            .then(response => response.json())
            .then(json => {
                setQuestion(json);
                if (!json) {
                    setAnswerItemList([]);
                    return;
                }
                setAnswerItemList((json.questionItemList || []).map(item => makeAnswerItem(item)));
            });
    };

    const onChangeScore = (id, score) => {
        answerItemList.forEach(item => {
            if (item.questionItemId === id) {
                item.score = score;
            }
        });

        setAnswerItemList(answerItemList);
    };

    return (
        <Typography component={"div"}>
            <Paper className={classes.paper} onClick={handleClickOpen} >
                <Typography variant="h5" component="h3" >
                    {props.name}
                </Typography>
                <Typography component="p">
                    {props.description}
                </Typography>
            </Paper>
            <Dialog fullScreen={true} open={open} onClose={handleClose} onEnter={handleEnter}>
                <AppBar position="fixed">
                    <Toolbar className={classes.toolbar}>
                        <IconButton edge="start" color="inherit" onClick={handleClose} aria-label="close">
                            <CloseIcon />
                        </IconButton>
                        <Typography variant="h5" className={classes.title} align={'center'}>
                            {question.name}
                        </Typography>
                        <Button color="inherit" className={classes.saveBtn} onClick={handleSave}>
                            SAVE
                        </Button>
                    </Toolbar>
                </AppBar>
                <Box p={2} style={{paddingTop:'90px'}}>
                    <p dangerouslySetInnerHTML={{__html: question.description}}/>
                </Box>
                <List>
                    {
                        (answerItemList||[]).map(item => {
                            return (
                                <ListItem {...a11yProps('survey-item-list')(item.questionItemId)} divider={true}>
                                    <ListItemText
                                        primary={item.description}
                                        secondary={
                                            <ScoreGroup {...a11yProps('score-group')(item.questionItemId)}
                                                        uid={item.questionItemId}
                                                        onChange={v => onChangeScore(item.questionItemId, v)}
                                                        {...item}/>
                                        }/>
                                </ListItem>
                            );
                        })
                    }
                </List>
            </Dialog>
        </Typography>
    );
}