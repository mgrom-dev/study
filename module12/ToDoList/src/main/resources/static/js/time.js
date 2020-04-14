(function time(){
    let divTime = document.querySelector(".time");
    if (divTime) {
        let date = new Date();
        let weekday = ["воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"][date.getDay()];
        let day = (date.getDate() < 10 ? "0" : "") + date.getDate();
        let month = (date.getMonth() < 9 ? "0" : "") + (date.getMonth() + 1);
        let year = date.getFullYear();
        let hour = (date.getHours() < 10 ? "0" : "") + date.getHours();
        let minutes = (date.getMinutes() < 10 ? "0" : "") + date.getMinutes();
        let seconds = (date.getSeconds() < 10 ? "0" : "") + date.getSeconds();
        divTime.innerHTML = "Сегодня: " + weekday + " " +
                    day + "." + month + "." + year + "г.&nbsp;&nbsp;" +
                    hour + ":" + minutes + ":" + seconds;
        setTimeout(time, 1000);
    }
})();