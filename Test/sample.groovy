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
result = string.replaceAll( /[^\d]/ ,"-");
println result;
