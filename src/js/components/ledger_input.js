import React, {useState} from "react";
import PropTypes from "prop-types";

export default function LedgerInput(props) {
  return <input type="text"
                className="money-input"
                value={props.value}
                onChange={(e) => props.onChange(e.target.value)}
                onBlur={() => props.onCommit(props.value)}
                onKeyPress={(e) => {
                  if (e.key === "Enter") {
                    props.onCommit(props.value);
                  }
                }}
                />;
}

LedgerInput.propTypes = {
  value: PropTypes.string,
  onChange: PropTypes.func,
  onCommit: PropTypes.func,
};
