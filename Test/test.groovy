@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 1
}

// <メソッドの定義>
// 字取りメソッド
def jidoriBuilder(jidori,sei,mei,pSei,pMei,span,positionX){
  j = jidori.size() - 1;
  for(i=0; i<jidori.size(); i++){
    if(sei.length() == jidori[i][0] && mei.length() == jidori[i][1]){
      smspan = span * jidori[i][2];
      //姓名にカーニング追加
      pSei.param.letterSpacing = jidori[i][3];
      pMei.param.letterSpacing = jidori[i][4];
      break;
    }else{
      smspan = span * jidori[j][2];
      pSei.param.letterSpacing = jidori[j][3];
      pMei.param.letterSpacing = jidori[j][4];
    }
  }
  pSei.transform.translateX = positionX;
  pMei.transform.translateX = positionX + pSei.boundBox.width + smspan;
}

//住所欄段落数計算メソッド
def sumlines = 0;
def calclines(sumlines,recordList){
  for (int i=0; i<recordList.size(); i++){
    if (recordList[i].length()>0){
      sumlines += 1;
    }else{
      sumlines += 0;
    }
  }
  sumlines;
}

//段落自動取詰(下基準)メソッド
def linespan = 0;
def lineheight = 0;
def positionY = 0;
def positionX = 0;
def paragraphBuilder(recordList,partsList,positionY,linespan,lineheight){
  i = recordList.size() - 1;
  for(i; i>-1; i--){
    partsList[i].transform.translateY = positionY - linespan;
    if(recordList[i]==''){
      partsList[i].setDisplay("none");
      linespan += 0;
    }else{
      linespan += lineheight;
    }
  }
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  //基本関数
 labelList.each {
   injectionOneParts(cassette, it , record, imageTable);
 }
  //変数取得
  def title1 =record['肩書き1'];
  def title2 =record['肩書き2'];
  def title3 =record['肩書き3'];
  def sei = record['姓'];
  def mei = record['名'];
  def postnum1 = record['郵便番号'];
  def adr1 = record['住所1'];
  def adr12 = record['住所1の2行目'];
  def tel1 = record['TEL1'];
  def fax1 = record['FAX1'];
  def mobile = record['携帯'];
  def email = record['Email'];
  def adrName2 = record['住所2の名称'];
  def postnum2 = record['郵便番号2'];
  def adr2 = record['住所2'];
  def tel2 = record['TEL2'];
  def fax2 = record['FAX2'];
  def url = record['URL'];
  if (url=='なし'){url=''};
  def addressList = [adr1,adr12,tel1,mobile,email,adrName2,adr2,tel2,url];

/*
   //デバッグ出力
    def debug = ['デバッグ'];
    record['デバッグ'] = "test:";
    debug.each{
      injectionOneParts(cassette, it , record, imageTable);
    }
*/

  //<表面住所データ結合>
  def adr1lLabelList = ["結合住所"];
  def address1 = '〒' + postnum1 + ' ' + adr1;
  record['結合住所'] = address1;
  adr1lLabelList.each{
   injectionOneParts(cassette, it , record, imageTable);
 }

  //<表面電話番号結合>
  def telfax1LabelList = ['TEL1結合'];
  def telfax1= 'TEL ' + tel1 + '/FAX ' + fax1 ;
  record['TEL1結合'] = telfax1;
  telfax1LabelList.each{
    injectionOneParts(cassette, it , record, imageTable);
  }

  //<表面住所データ結合2>
  def adr2LabelList = ["結合住所2"];
  def address2 = '〒' + postnum2 + ' ' + adr2;
  record['結合住所2'] = address2;
  adr2LabelList.each{
   injectionOneParts(cassette, it , record, imageTable);
 }

  //<表面電話番号結合2>
  def telfax2LabelList = ['TEL2結合'];
  def telfax2= 'TEL ' + tel2 + '/FAX ' + fax2 ;
  record['TEL2結合'] = telfax2;
  telfax2LabelList.each{
    injectionOneParts(cassette, it , record, imageTable);
  }

  //ISOの有無
  def pImageLabelList = ['ISOロゴ'];
  pImage = 'c64cf821c0a800292cdae106bc9f1b37';
  record['ISOロゴ'] = pImage;
  pImageLabelList.each{
    injectionOneParts(cassette, it , record, imageTable);
  }

  //表面の判定
  def omote = getPartsByLabel('肩書き1', 1, cassette) ;
  //表面の処理ここから
  if(omote != null){
    //表面のパーツ操作スクリプト
    //関連パーツ取得
    def pTitle1 = getPartsByLabel('肩書き1',1,cassette);
    def pTitle2 = getPartsByLabel('肩書き2',1,cassette);
    def pTitle3 = getPartsByLabel('肩書き3',1,cassette);
    def pSei = getPartsByLabel('姓',1,cassette);
    def pMei = getPartsByLabel('名',1,cassette);
    def pAdr1 = getPartsByLabel('結合住所',1,cassette);
    def pAdr12 = getPartsByLabel('住所1の2行目',1,cassette);
    def pTelFax1 = getPartsByLabel('TEL1結合',1,cassette);
    def pMobile1 = getPartsByLabel('携帯',1,cassette);
    def pEmail1 = getPartsByLabel('Email',1,cassette);
    def pAdrName2 = getPartsByLabel('住所2の名称',1,cassette);
    def pAdr2 = getPartsByLabel('結合住所2',1,cassette);
    def pTelFax2 = getPartsByLabel('TEL2結合',1,cassette);
    def pURL = getPartsByLabel('URL',1,cassette);
    def pAddressList = [pAdr1,pAdr12,pTelFax1,pMobile1,pEmail1,pAdrName2,pAdr2,pTelFax2,pURL];

    //住所欄の行数計算
    sumlines = 0;
    sumlines = calclines(sumlines,addressList);

    //氏名のY位置
    positionY = 34;
    if(sumlines > 5){positionY = 32;}
    if(sumlines > 7){positionY = 29;}
    pSei.transform.translateY = positionY;
    pMei.transform.translateY = positionY;

    //字取り
    //デフォルト設定
    positionX = 28;
    def span = 5;//全角スペース1個分(mm)
    //JidoriX[姓文字数、名文字数、姓名間全角スペース比、姓スペース(pt)、名スペース(pt)]
    def jidori = [
      [1,1,2,0,0],
      [1,2,2,0,0],
      [1,3,2,7,7],
      [2,1,2,7,7],
      [2,2,1,7,7],
      [2,3,1,7,7],
      [3,1,2,7,7],
      [3,2,1,7,7],
      [3,3,1,3.5,3.5],
      [0,0,0.5,0,0]
      ];
//    jidoriBuilder(jidori,sei,mei,pSei,pMei,span,positionX);

if(sei.length() == jidori[0][0] && mei.length() == jidori[0][1]){
  smspan = span * jidori[0][2];
  //姓名にカーニング追加
  pSei.param.letterSpacing = jidori[0][3];
  pMei.param.letterSpacing = jidori[0][4];
}else if(sei.length() == jidori[4][0]  && mei.length() == jidori[4][1] ){
  smspan = span * jidori[4][2];
  pSei.param.letterSpacing = jidori[4][3];
  pMei.param.letterSpacing = jidori[4][4];
}else if(sei.length() == jidori[8][0]  && mei.length() == jidori[8][1] ){
  smspan = span * jidori[8][2];
  pSei.param.letterSpacing = jidori[8][3];
  pMei.param.letterSpacing = jidori[8][4];
}else {
smspan = span * 0.5;
pSei.param.letterSpacing = 0;
pMei.param.letterSpacing = 0;
}
pSei.transform.translateX = positionX;
pMei.transform.translateX = positionX + pSei.boundBox.width + smspan;


    //肩書ききが空の場合段落を取る詰めする
    def titleList = [title1,title2,title3];
    def pTitleList = [pTitle1,pTitle2,pTitle3];
    linespan = 0;
    lineheight = 2.5;
    positionY = pSei.boundBox.y - 2;
    if(sumlines > 7){pSei.boundBox.y - 1.5;}
    paragraphBuilder(titleList,pTitleList,positionY,linespan,lineheight);

    //住所行が空の場合段落を取詰する
    linespan = 0;
    lineheight = 2.5;
    positionY = 51.5;

    paragraphBuilder(addressList,pAddressList,positionY,linespan,lineheight);

  }else{
    //裏面のパーツ操作スクリプト
  }

}
