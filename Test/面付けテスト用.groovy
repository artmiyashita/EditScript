@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(1, env.imageTable); // ページ数 1
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  //def additionalLabelList = [''];

  //基本関数
  labelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }

  //追加ラベルへの差し込み
  /*
  additionalLabelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }
*/
  //表面の判定
  def omote = getPartsByLabel('肩書き1', 1, cassette) ;
  //表面の処理ここから
  if(omote != null){
    //表面のパーツ操作スクリプト
    //関連パーツ取得
    def pURL = getPartsByLabel('URL',1,cassette);
    pURL.transform.translateY = 28;

  }
}
