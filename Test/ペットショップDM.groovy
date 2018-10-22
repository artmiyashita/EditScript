@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 2
}

// <メソッドの定義>
//改行メソッド
def newline(text,list,string,line,count){
  while (text.length() > count){
    list[line] = text.substring(0,count);
    text = text.substring(count);
    line += 1;
  }
  list[line] = text;
  for (i = 0; i <= line-1; i++){
    string += list[i] + '\n';
  }
  string + list[line]
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  //郵便番号整形
  def additionalLabelList = ['郵便バーコード','氏名','撮影日','本文','おすすめ商品名2価格','獲得ポイント',];
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

  def nowPoint = record['現在ポイント'];
  nowPoint = Integer.decode(nowPoint);
  nowPoint = Math.ceil(nowPoint * 0.1);
  record['獲得ポイント'] = (int)nowPoint;

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
    def pTest1 = getPartsByLabel('テスト1', 1, cassette);
    //pTest1.editReferencePoint('top-left',keepReferencePointPosition = true);
    pTest1.setDisplay("none");

    //地図
/*
    def map = record['地図'];
    def pMap = getPartsByLabel('地図画像', 1, cassette);
    if (map =='MAP1'){
      pMap.param.trackingID = '3e60acdac0a800295231fe5f4ab342c1';
    }else if(map =='MAP2'){
      pMap.param.trackingID = '5bb26618c0a800294a22753eaa16a0df';
    }else if(map =='MAP3'){
      pMap.param.trackingID = '5bb27d21c0a800294a309bf367bc7c57';
    }
*/

  }else{//以下、裏面の処理

    //本文改行
    def pContents = getPartsByLabel('本文', 1, cassette);
    def contents = pContents.param.text;
    def contentsList = [];
    def string = '';
    def l = 0;
    def limit = 40;
    pContents.param.text = newline(contents,contentsList,string,l,limit);

    //おすすめ商品
    def recommendItem1 = record['おすすめ商品名1'];
    def recommendItemlist2 =[
      ['ナチュラルダイエット','8bb3f940c0a800297bb668d676dd05c3'],
      ['贅沢キャットフード','8bb560a7c0a800293ad193d169149119'],
      ['バランスドッグフード','8bb8da71c0a8002933740ee5d69b5d82'],
      ['お出かけキャリー','8bba3a2bc0a8002924f0825061167d4d']
    ]
    def pRecommendItem1 = getPartsByLabel('おすすめ商品画像', 1, cassette);
    for(i=0; i<recommendItemlist2.size(); i++){
      if (recommendItem1 == recommendItemlist2[i][0]){
        //pRecommendItem1.param.trackingID = recommendItemlist[i][1];
        replaceCassette(pRecommendItem1,recommendItemlist2[i][1]);
      }
    }

    //おすすめ商品情報改行
    def pRecommendInfo = getPartsByLabel('おすすめ商品情報', 1, cassette);
    def recommendInfo = record['おすすめ商品情報'];
    def infoList = [];
    string = '';
    l = 0;
    limit = 20;
    searchWord = '◆';
    foundIndex = 1;
    foundIndex = recommendInfo.indexOf(searchWord);
    while (foundIndex >= 0){
      infoList[l] = recommendInfo.substring(0,foundIndex);
      recommendInfo = recommendInfo.substring(foundIndex+1);
      foundIndex = recommendInfo.indexOf(searchWord);
      l += 1;
    }
    pRecommendInfo.param.text = newline(recommendInfo,infoList,string,l,limit);

    //カセット「情報枠」入れ替え
    def birthday = record['お誕生日']
    birthday = new Date(birthday);
    def birth_m = birthday.getMonth() + 1;
    def today = new Date();
    def next_m = today.getMonth() + 1 + 1;
    if(next_m == birth_m){
      def pInfomation = getPartsByLabel('情報枠', 1, cassette);
      replaceCassette(pInfomation,'5d856ce6c0a8002935c2d6dc214e569a');
      //replaceCassette(pInfomation,'7fdda23dc0a800297e2dc9aeae16f5b0');
    }

    //情報枠に情報を記載
    infoList[];
    infoList[0] = '一緒にご来店キャンペーン開催中！' + record['動物名'] + 'とご来店になると、50ポイントを進呈します';
    infoList[1] = birth_m + '生まれの' + record['動物名'] + 'に素敵なプレゼントを差し上げます！このハガキをお持ちください';
    def info = 0;
    if(next_m == birth_m){
      info = 1;
    }
    def pInfobar = getPartsByLabel('情報', 1, cassette);
    contents = infoList[info];
    contentsList = [];
    string = '';
    l = 0;
    limit = 14;
    pInfobar.param.text = newline(contents,contentsList,string,l,limit);

    def test3 = getPartsByLabel('テスト3', 1, cassette);
    //test3.param.text = 'Xの倍率：'+ pRecommendItem1.transform.scaleX + '　Yの倍率：' + pRecommendItem1.transform.scaleY;
    test3.setDisplay("none");

  }
}
