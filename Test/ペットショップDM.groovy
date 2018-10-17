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
  def additionalLabelList = ['郵便バーコード','氏名','撮影日','本文','おすすめ商品名2価格'];
  postnum = record['郵便番号'].replace('-','');
  record['郵便バーコード'] = postnum;

  //氏名結合
  def sei = record['姓'];
  def mei = record['名'];
  record['氏名'] = sei + ' ' + mei;

  //来店日整形
  def strdate = record['前回来店日'];
  def wordList = [];
  wordList = strdate.split('/',0);
  def filmdate = wordList[0] + '年' + wordList[1] + '月' + wordList[2] + '日';
  record['撮影日'] = '(' + filmdate  + ' ご来店時に撮影)';

  //おすすめ商品名2&価格
  def recommendItem2 = record['おすすめ商品名2'];
  def recommendItemPrice = record['おすすめ商品価格'];
  record['おすすめ商品名2価格'] = recommendItem2 + '/' + recommendItemPrice;

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

    //テスト
    /*
    def pTest1 = getPartsByLabel('テスト1', 1, cassette);
    pTest1.editReferencePoint('top-left',keepReferencePointPosition = true);
*/
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

  }else{//以下、裏面の処理

    //本文改行
    def pContents = getPartsByLabel('本文', 1, cassette);
    def contents = pContents.param.text;
    def contentsList = [];
    def string = '';
    def l = 0;
    while (contents.length() > 40){
      contentsList[l] = contents.substring(0,40);
      contents = contents.substring(40);
      l += 1;
    }
    contentsList[l] = contents;
    for (i=0; i <= l-1; i++){
      string += contentsList[i] + '\n';
    }
    pContents.param.text = string + contentsList[l];

    //おすすめ商品
    def recommendItem1 = record['おすすめ商品名1'];
    def recommendItemlist =[
      ['ナチュラルダイエット','3e11d72bc0a800293d76e3e80807dc2f'],
      ['贅沢キャットフード','7a90368ac0a8002928a86ce215866858'],
      ['バランスドッグフード','7a8ffcf5c0a800292fd5f77a4be496e7'],
      ['お出かけキャリー','7a90778fc0a800297c8e81053329efc7']
    ]
    def pRecommendItem1 = getPartsByLabel('おすすめ商品画像', 1, cassette);
    for(i=0; i<recommendItemlist.size(); i++){
      if (recommendItem1 == recommendItemlist[i][0]){
        pRecommendItem1.param.trackingID = recommendItemlist[i][1];
      }
    }

    //おすすめ商品情報改行
    def recommendInfo = record['おすすめ商品情報'];
    def pRecommendInfo = getPartsByLabel('おすすめ商品情報', 1, cassette);
    def infoList = [];
    string = '';
    l = 0;
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
    for (i = 0; i <= l-1; i++){
      string += infoList[i] + '\n';
    }
    pRecommendInfo.param.text =  string + infoList[l];

    //カセット「情報枠」入れ替え

    def birthday = record['お誕生日']
    birthday = new Date(birthday);
    def birth_m = birthday.getMonth() + 1;
    def today = new Date();
    def next_m = today.getMonth() + 1 + 1;
    if(next_m == birth_m){
      def pInfomation = getPartsByLabel('情報枠', 1, cassette);
      //replaceCassette(pInfomation,'5d856ce6c0a8002935c2d6dc214e569a');
      replaceCassette(pInfomation,'7fdda23dc0a800297e2dc9aeae16f5b0');
    }

    //def test3 = getPartsByLabel('テスト3', 1, cassette);
    //test3.param.text = 'Xの倍率：'+ pRecommendItem1.transform.scaleX + '　Yの倍率：' + pRecommendItem1.transform.scaleY;

  }
}
