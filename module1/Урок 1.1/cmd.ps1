$FormatEnumerationLimit = -1 #отключение органичения вывода (убирает многоточия)
cd "F:\Рабочий стол\Обучение\skillbox"
ls -s
$weather = wget "http://api.weatherstack.com/current?access_key=aaa9131a3b487ccaacd16d8189db6295&query=London"
$weather.content | Out-File "F:\Рабочий стол\Обучение\skillbox\Домашние задания\Урок 1.1\weather.txt"
$weather.content
pause