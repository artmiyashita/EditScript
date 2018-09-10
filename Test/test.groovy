@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 1
}

// メソッドの定義
def insertspan(sei,mei,seispan,smspan,meispan){
  for (int i = 1; i < sei.length(); i+=2)
  {
    sei.insert(i,seispan);
  }
  for (int i = 1; i < mei.length(); i+=2)
  {
    mei.insert(i,meispan);
  }
  sei + smspan + mei;
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  //基本関数
 labelList.each {
   injectionOneParts(cassette, it , record, imageTable);
 }

  //ラベルの追加
  def additionalLabelList1 = ["結合住所"];

  //表面住所データ結合
  def postnum = record['郵便番号'];
  def adr1 = record['住所1'];
  def adr2 = record['住所2'];
  def address = postnum + adr1 + adr2;

  record['結合住所'] = address;

 //追加ラベルへの差し込み
 additionalLabelList1.each{
   injectionOneParts(cassette, it , record, imageTable);
 }

  //姓名字取り
  def additionalLabelList2 = ["氏名"];

  StringBuilder sei = new StringBuilder();
  sei.append(record['姓']);

  StringBuilder mei = new StringBuilder();
  mei.append(record['名']);

  def seispan = '';
  def smspan = ' ';
  def meispan = '';
  def shimei = '';

  if (sei.length() == 2 && mei.length() == 2)
  {
    seispan = ' ';
    smspan = '　';
    meispan = ' ';
    shimei  = insertspan(sei,mei,seispan,smspan,meispan)
  }
  else if (sei.length() == 3 && mei.length() == 3)
  {
    seispan = '';
    smspan = ' ';
    meispan = '';
    shimei  = insertspan(sei,mei,seispan,smspan,meispan)
  }
  else
  {
    shimei  = insertspan(sei,mei,seispan,smspan,meispan)
  }

  record['氏名'] = shimei;

  //追加ラベルへの差し込み
  additionalLabelList2.each{
    injectionOneParts(cassette, it , record, imageTable);
  }

  //表面の判定
  def omote = getPartsByLabel('肩書き1', 1, cassette) ;
  //表面の処理ここから
  if(omote != null){
    //表面のパーツ操作スクリプト
    //関連パーツ取得
    def title1 = getPartsByLabel('肩書き1',1,cassette);
    def title2 = getPartsByLabel('肩書き2',1,cassette);
    def title3 = getPartsByLabel('肩書き3',1,cassette);
    //肩書ききが空の場合Y座標を変更
    if(title2.param.text == ''){
      title1.transform.translateY = 18;
    }
    if(title3.param.text == ''){
      title2.transform.translateY = 21;
      title1.transform.translateY = 18;
    }
    if(title2.param.text == '' && title3.param.text == ''){
      title1.transform.translateY = 21;
    }
  }else{
    //裏面のパーツ操作スクリプト
  }

}
