import React, {useState} from 'react';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Dialog from "@material-ui/core/Dialog/Dialog";
import IconButton from "@material-ui/core/IconButton/IconButton";
import {makeStyles} from "@material-ui/core";
import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";
import Toolbar from "@material-ui/core/Toolbar/Toolbar";
import CloseIcon from '@material-ui/icons/Close';
import ListItemAvatar from "@material-ui/core/ListItemAvatar/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar/Avatar";

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
    avatar : {
        width: '120px',
        height: '120px',
        fontSize: '45px',
        margin: '20px',
        backgroundColor: 'coral'
    },
    listText : {
        primary : {
            fontSize: '2rem'
        }
    }
}));


export default function SurveyResItem(props) {
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const [surveyResult, setSurveyResult] = useState({});

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setSurveyResult({});
        setOpen(false);
    };

    const handleEnter = () => {
        fetch(`/api/surveys/${props.surveyId}/result`)
            .then(response => response.json())
            .then(json => {
                if (json.result) {
                    setSurveyResult(json.data);
                } else {
                    alert(`설문 결과를 조회에 실패하였습니다. message : ${json.message}`);
                }
            });
    };


    return (
        <div>
            <Paper className={classes.paper} onClick={handleClickOpen} >
                <Typography variant="h5" component="h3" >
                    {props.name} 설문결과
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
                        {`${props.name} 설문결과`}
                    </Typography>
                </Toolbar>
                <List>
                    <ListItem button>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar} variant={"rounded"}>
                                {surveyResult.participate}
                            </Avatar>
                        </ListItemAvatar>
                        <Typography variant="h5" component="h3" >
                            설문 참여 인원
                        </Typography>
                    </ListItem>
                    <ListItem button>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar} variant={"rounded"}>
                                {surveyResult.averageByQuestion}
                            </Avatar>
                        </ListItemAvatar>
                        <Typography variant="h5" component="h3" >
                            질문별 평균
                        </Typography>
                    </ListItem>
                    <ListItem button>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar} variant={"rounded"}>
                                {surveyResult.totalAverage}
                            </Avatar>
                        </ListItemAvatar>
                        <Typography variant="h5" component="h3" >
                            설문총점 평균
                        </Typography>
                    </ListItem>
                    <ListItem button>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar} variant={"rounded"}>
                                {surveyResult.totalScore}
                            </Avatar>
                        </ListItemAvatar>
                        <Typography variant="h5" component="h3" >
                            설문총점
                        </Typography>
                    </ListItem>
                </List>
            </Dialog>
        </div>
    );
}