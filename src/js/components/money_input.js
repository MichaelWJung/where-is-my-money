import {useState} from "react";
import PropTypes from "prop-types";

export default function MoneyInput(props) {
  const [value, setValue] = useState(props.value);
  return <input type="text"
                className="money-input"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                onBlur={() => props.onCommit(value)}
                onKeyPress={(e) => {
                  if (e.key === "Enter") {
                    props.onCommit(value);
                  }
                }}
                />;
}

MoneyInput.propTypes = {
  value: PropTypes.number,
  onCommit: PropTypes.func,
};
