<html>
<body>
<h1>Weather report for ${city} on ${date}</h1>
<ul>
    <li>Weather: ${overall_description}</li>
    <li>Sunrise is at ${sunrise} (${timeZoneID} time)</li>
    <li>Sunset is at ${sunset} (${timeZoneID} time)</li>
    <li>The temperature is ${temperaturecelsius} Celsius</li>
    <li>The temperature is ${temperaturefahrenheit} Fahrenheit</li>
</ul>   
    <p style="color: red">${error_message}</p>
    
    <a href="/WeatherWebApp">Back</a>
</body>
</html>