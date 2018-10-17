import java.util.Calendar;
import groovy.time.TimeCategory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

def string = "abc/defg/hi";
def searchWord = "/";

int foundIndex = 1;
def indexList = [];
def wordList = [];
/*
while (0 <= foundIndex){
    foundIndex = string.indexOf(searchWord);
    println "index:" + foundIndex;

    indexList.add(foundIndex);
    println "indexList:" + indexList;

  if (0 <= foundIndex){
    wordList.add(string.substring(0,foundIndex));
    println "wordList:" + wordList;

    def nextIndex = foundIndex + searchWord.length();
    string = string.substring(nextIndex);
    println "string:" + string;

  }else{
    wordList.add(string);
    println "wordList:" + wordList;
    break;
  }
}
*/
/*

searchWord = "-";
string = tel1;
string = string.replaceAll(/[\uff01-\uff5f]/){new String((char)(((int)it)-65248))}
string = string.replaceAll(/\d*\.\d*\.\d*\/,"-")

println "電話番号:" + string;
*/
def tel1 = "０３-１２３４-５６７８";
string = tel1;

string = string.replaceAll(/[\uff01-\uff5f]/){new String((char)(((int)it)-65248))}
if (string ==~ /\d*.\d*.\d*/) {
  println "match" + string;
} else {
  println "don't match" + string;
}
string = string.substring(1);
result = string.replaceAll( /[^\d]/ ,"-");

println result;

/*現在字間取得
import java.text.SimpleDateFormat

def date = new Date()
println date;
def format = new SimpleDateFormat('yyyy/MM/dd HH:mm:ss.SSS')
def formatDate = format.format(date);
println 'Date型' + formatDate;
*/

/*文字列日付を年月日に
def strdate = '2018/10/9'
foundIndex = 1;
wordList = [];
wordList = strdate.split('/',0);
def filmdate = wordList[0] + '年' + wordList[1] + '月' + wordList[2] + '日';
println '撮影日：' + filmdate;
*/

//date型への変更
testday = new Date("2011/1/1");
testday = testday//.format("yyyy/MM/dd")
println testday;
today = new Date()//.format("yyyy/MM/dd")
println today;

//date型の分解
testday_m = testday.getMonth() + 1;
println testday_m;
today_m = today.getMonth() + 1;
println today_m;
next_m = today_m + 1;
if(next_m > 12){next_m=1}
if(next_m == testday_m){
  println '来月は誕生月です'
}else{
  println '通常'
}

//TimeCategoryによる計算
/*
def acceptedFormat = "yyyy-MM-dd"
def today = new Date()
def currentdate = today.format(acceptedFormat)

use(TimeCategory) {
    def oneYear = today + 1.month
    println '1年加算：' + oneYear

    def ninetyDays = today + 90.days
    println '90日加算：' + ninetyDays
}
*/

//日付の加算
/*


def now = LocalDateTime.now()
println now
def ldt = now.plusDays(10)

def formatter = DateTimeFormatter.ofPattern('yyyy/MM/dd HH:mm:ss.SSS')
println ldt.format(formatter)
*/

//日付の比較
/*
Date d1 = new GregorianCalendar(2018, 4, 1).getTime();
Date d2 = new GregorianCalendar(2018, 4, 2).getTime();
int result = d1.compareTo(d2)
println result;
*/
