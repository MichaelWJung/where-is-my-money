import React, {useState} from 'react';
import LedgerInput from '../components/ledger_input';

export default {
  title: 'LedgerInput',
  component: LedgerInput,
};

const Template = (args) => {
  const [value, setValue] = useState(args.value ?? '');
  return (
    <LedgerInput {...args}
      onChange={(...params) => {
        args.onChange(...params);
        setValue(...params);
      }}
      value={value}
    />);
}

export const Normal = Template.bind({});
Normal.args = {
  value: "1000.00",
};
