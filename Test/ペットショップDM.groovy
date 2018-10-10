@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 2
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  //郵便番号整形
  def additionalLabelList = ['郵便バーコード','氏名','撮影日'];
  postnum = record['郵便番号'].replace('-','');
  record['郵便バーコード'] = postnum;

  //氏名結合
  def sei = record['姓'];
  def mei = record['名'];
  record['氏名'] = sei + ' ' + mei;

  //来店日整形
  def strdate = record['前回来店日'];
  def foundIndex = 1;
  def wordList = [];
  def searchWord = '/';
  foundIndex = strdate.indexOf(searchWord);
  if (foundIndex >= 0){
    while (foundIndex >= 0){
      wordList.add(strdate.substring(0,foundIndex));
      strdate = strdate.substring(foundIndex+1);
      foundIndex = strdate.indexOf(searchWord);
    }
    wordList.add(strdate);
  }
  def filmdate = wordList[0] + '年' + wordList[1] + '月' + wordList[2] + '日';
  record['撮影日'] = '(' + filmdate  + ' ご来店時に撮影)';

  //基本関数
  labelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }
  //追加ラベルへの差し込み
  additionalLabelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }

  //表面の判定
  def omote = getPartsByLabel('郵便番号', 1, cassette) ;
  //表面の処理ここから
  if(omote != null){

    for (int i=0; i<7; i++){
      getPartsByLabel('郵便番号',i+1,cassette).param.text = postnum.substring(i,i+1);
    }

    //地図
    def map = record['地図'];
    def pMap = getPartsByLabel('地図画像', 1, cassette);
    if (map =='MAP1'){
      pMap.param.trackingID = '3e60acdac0a800295231fe5f4ab342c1';
    }else if(map =='MAP2'){
      pMap.param.trackingID = '5bb26618c0a800294a22753eaa16a0df';
    }else if(map =='MAP3'){
      pMap.param.trackingID = '5bb27d21c0a800294a309bf367bc7c57';
    }

  }else{

    //おすすめ商品情報整形

    def recommendInfo = record['おすすめ商品情報'];
    def pRecommendInfo = getPartsByLabel('おすすめ商品情報', 1, cassette);
    def infoList = [];
    def l = 0;
    searchWord = '◆';
    foundIndex = 1;
    foundIndex = recommendInfo.indexOf(searchWord);
    while (foundIndex >= 0){
      infoList[l] = recommendInfo.substring(0,foundIndex);
      recommendInfo = recommendInfo.substring(foundIndex+1);
      foundIndex = recommendInfo.indexOf(searchWord);
      l += 1;
    }
    while (recommendInfo.length() > 20){
      infoList[l] = recommendInfo.substring(0,20);
      recommendInfo = recommendInfo.substring(20);
      l += 1;
    }
    infoList[l] = recommendInfo;
    for (i=0; i <= 2; i++){
      infoList[i] = infoList[i] + '\n';
    }
    pRecommendInfo.param.text = infoList[0] + infoList[1] + infoList[2];

  }
}
