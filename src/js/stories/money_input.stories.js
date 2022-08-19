import MoneyInput from '../components/money_input';

export default {
  title: 'MoneyInput',
  component: MoneyInput,
};

const Template = (args) => <MoneyInput {...args} />;

export const Normal = Template.bind({});
Normal.args = {
  value: 1000,
};
