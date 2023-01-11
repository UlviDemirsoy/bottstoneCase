import './App.css';
import React, {useState} from 'react';
import DatePicker from 'react-datepicker';
import Dropdown from 'react-dropdown';
import "react-datepicker/dist/react-datepicker.css";
import 'react-dropdown/style.css';
import Paper from '@mui/material/Paper';
import {
  Chart,
  BarSeries,
  Title,
  ArgumentAxis,
  ValueAxis,
} from '@devexpress/dx-react-chart-material-ui';
import { Animation } from '@devexpress/dx-react-chart';

const options = [
  'day', 'week', 'month'
];

function formatDate(date) {
  var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

  if (month.length < 2) 
      month = '0' + month;
  if (day.length < 2) 
      day = '0' + day;

  return [year, month, day].join('-');
}


function App() {

  const [selectedDate, setSelectedDate] = useState(new Date("2023-01-01"));
  const [selectedDate2, setSelectedDate2] = useState(new Date("2023-04-04"));
  const [selectedDropDown, setselectedDropDown] = useState(options[0]);
  
  const defaultOption = options[0];
  
  const [data, setData] = useState({data: []});
  const [isLoading, setIsLoading] = useState(false);
  const [err, setErr] = useState('');

  const handleClick = async () => {
    setIsLoading(true);

    try {
      console.log(selectedDropDown['value']);
      var d1 = formatDate(selectedDate);
      var d2 = formatDate(selectedDate2);
      let url = `http://localhost:8080/api/resources?type=${selectedDropDown['value']}&start=${d1}&end=${d2}`;

      const response = await fetch(url
      , {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Content-Type' : 'application/json',
          'Access-Control-Allow-Origin': 'http://localhost:3000',
          'Access-Control-Allow-Credentials': 'true'
        },
      });

      if (!response.ok) {
        throw new Error(`Error! status: ${response.status}`);
      }

      const result = await response.json();

      console.log('result is: ', JSON.stringify(result, null, 4));

      setData(result);
    } catch (err) {
      setErr(err.message);
    } finally {
      setIsLoading(false);
    }
  };


  return (
    <div className="App">
      <header className="App-header">
        
       
      </header>
      <div>
        
      <Paper>
        <Chart
          data={data}
        >
          <ArgumentAxis />
          <ValueAxis />

          <BarSeries
            valueField="resourcesInUse"
            argumentField="dateinfo"
          />
          <Title text="Resources" />
          <Animation />
        </Chart>
      </Paper>
      <Dropdown id='dropdown' options={options} value={selectedDropDown} onChange={selectedDropDown => setselectedDropDown(selectedDropDown) } />

        <DatePicker className='datepicker' id='dp1' selected={selectedDate}
          
            onChange={date => setSelectedDate(date)} 
            placeholderText = "Start Date"
            />

          <DatePicker className='datepicker' id='dp2' selected={selectedDate2}
          
            onChange={date => setSelectedDate2(date)} 
          placeholderText = "End Date"/>

      <button onClick={handleClick}>Fetch Data</button>
      </div>
    </div>
  );
}

export default App;
