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

def tel1 = "０３ー１２３４ー５６７８";
searchWord = "-";
string = tel1;
//全角半角変換
string = string.replaceAll(/[\uff01-\uff5f]/){new String((char)(((int)it)-65248))}
string = string.replaceAll("ー","-")
//-を()に変換
foundIndex = string.indexOf(searchWord);
if (foundIndex >= 0){
  wordList.add(string.substring(0,foundIndex));
  string = string.substring(foundIndex+1);
  foundIndex = string.indexOf(searchWord);
  wordList.add(string.substring(0,foundIndex));
  string = string.substring(foundIndex+1);
  wordList.add(string);
  string = wordList[0] + "(" + wordList[1] + ")" + wordList[2];
}
  println "電話番号:" + string;
