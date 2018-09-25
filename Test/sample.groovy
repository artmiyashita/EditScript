def string = "abc/defg/hi";
def searchWord = "/"

int foundIndex = 1;
def indexList = [];
def wordList = [];

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
