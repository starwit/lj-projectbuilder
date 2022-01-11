import {makeStyles} from "@mui/styles";

const LoadingButtonStyles = makeStyles(theme => ({
    button: {
        color: theme.palette.success.main,
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: '-12px',
        marginLeft: '-12px',
    }
}));
export default LoadingButtonStyles;