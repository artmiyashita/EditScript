@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(1, env.imageTable); // ページ数 1
}
//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {
 labelList.each {
 injectionOneParts(cassette, it , record, imageTable);
 }
}
