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
        <div>
            <Paper className={classes.paper} onClick={handleClickOpen} >
                <Typography variant="h5" component="h3" >
                    {props.name}
                </Typography>
                <Typography component="p">
                    {props.description}
                </Typography>
            </Paper>
            <Dialog fullScreen={true} open={open} onClose={handleClose} onEnter={handleEnter}>
                <Toolbar>
                    <IconButton edge="start" color="inherit" onClick={handleClose} aria-label="close">
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6" className={classes.title}>
                        {question.name}
                    </Typography>
                    <Button autoFocus color="inherit" onClick={handleSave}>
                        save
                    </Button>
                </Toolbar>
                <List>
                    {
                        (answerItemList||[]).map(item => {
                            return (
                                <ListItem {...a11yProps('survey-item-list')(item.questionItemId)} divider={true}>
                                    <ListItemText primary={item.description}
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
        </div>
    );
}