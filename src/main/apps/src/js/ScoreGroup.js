import RadioGroup from "@material-ui/core/RadioGroup/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel/FormControlLabel";
import Radio from "@material-ui/core/Radio/Radio";
import React, {useState} from "react";
import FormControl from "@material-ui/core/FormControl/FormControl";
import {a11yProps} from "./Util";

export default function ScoreGroup(props) {
    const [value, setValue] = useState(props.score || 0);
    const handleChange = event => {
        let v = parseInt(event.target.value);
        (props.onChange || function(){})(v);
        setValue(v);
    };

    return (
        <FormControl component="fieldset">
            <RadioGroup aria-label={`scoreGroup${props.uid}`} name={`scoreGroup${props.uid}`} value={value} onChange={handleChange} row={true}>
                {
                    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map(v =>
                        <FormControlLabel
                            value={v}
                            control={<Radio color="primary"/>}
                            label={v}
                            labelPlacement="end"
                            {...a11yProps('score-group-inner-radio')(v)}
                        />
                    )
                }
            </RadioGroup>
        </FormControl>
    );
}