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
      //合致パターンの記録
      jidoriId = i;
      //姓名間のスペースの倍率変更
      smspan = span * jidori[i][2];
      //姓名にスペース追加
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
//半角英数校正:全角英数字記号を半角に訂正
def fullwidthCorrector(alphanumericChar){
  def string = alphanumericChar.replaceAll(/[\uff01-\uff5f]/){new String((char)(((int)it)-65248))};
}
//
def replacer(string){
  string.replaceAll( /[^\d]/ ,"-");
}
//電話番号校正:電話番号をxxx(xxxx)xxxx型に訂正
def telnumBuilder(number){
  def wordList = [];
  def searchWord = "-";
  def foundIndex = number.indexOf(searchWord);
  if (foundIndex >= 0){
    while (foundIndex >= 0){
      wordList.add(number.substring(0,foundIndex));
      number = number.substring(foundIndex+1);
      foundIndex = number.indexOf(searchWord);
    }
    wordList.add(number);
    wordList[0] + "(" + wordList[1] + ")" + wordList[2];
  }else{
    number;
  }
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
def linespan = 0;//mm
def lineheight = 0;//mm
def positionY = 0;//mm
def positionX = 0;//mm
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

def seiRubyBuilder(seiruby,searchWord,pSeiRuby,pSei,rubySize,jidori,jidoriId){
  foundIndex = seiruby.indexOf(searchWord);
  if (foundIndex < 0){
    //姓ルビ(センター)配置
    if(seiruby){
      pSeiRuby.editReferencePoint('center-center',keepReferencePointPosition = false);
      pSeiRuby.transform.translateX = pSei.transform.translateX + pSei.boundBox.width / 2;
      pSeiRuby.param.maxWidth = pSei.boundBox.width;
      pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height - rubySpan;
    }
  } else {
    //姓ルビ(モノルビ)配置
    //姓ルビ(モノルビ)を区切り文字”/”で分解、配列に追加
    def seiRubyList = [];
    while (foundIndex >= 0){
      seiRubyList.push(seiruby.substring(0, foundIndex));
      seiruby = seiruby.substring(foundIndex+1);
      foundIndex = seiruby.indexOf(searchWord);
    }
    seiRubyList.push(seiruby);
    //姓ルビ(モノルビ)間の距離を算出
    def a = pSei.param.size;
    def b = rubySize;
    def c = jidori[jidoriId][3];
    def n = seiRubyList.size();
    def seiRubySpan = [];
    seiRubySpan[0] = (a - (b * seiRubyList[0].size()))/2;
    for (i=1; i<n; i++){
      seiRubySpan[i] = c + (2 * a - (b * (seiRubyList[i-1].size() + seiRubyList[i].size())))/2;
    }
    //姓ルビ(モノルビ)テキストの生成と配置
    def seiRubyText = '';
    for (i=0; i<n ; i++){
      seiRubyText += '<font size="' + seiRubySpan[i] + 'pt">　</font>' + seiRubyList[i];
    }
    pSeiRuby.param.text = '<p>' + seiRubyText + '</p>';
    pSeiRuby.transform.translateX = pSei.transform.translateX;
    pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height + (rubySize*0.35) - rubySpan;
  }
  r = pSeiRuby;
}

def function(pSeiRuby,rubySpan,pSei,seiruby,searchWord,rubySize,jidori,jidoriId){
  foundIndex = seiruby.indexOf(searchWord);
  if (foundIndex < 0){
    //姓ルビ(センター)配置
    if(seiruby){
      pSeiRuby.editReferencePoint('center-center',keepReferencePointPosition = false);
      pSeiRuby.transform.translateX = pSei.transform.translateX + pSei.boundBox.width / 2;
      pSeiRuby.param.maxWidth = pSei.boundBox.width;
      pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height - rubySpan;
      pSeiRuby.param.text = "ルビのテスト";
    }
  } else {
    //姓ルビ(モノルビ)配置
    //姓ルビ(モノルビ)を区切り文字”/”で分解、配列に追加
    def seiRubyList = [];
    while (foundIndex >= 0){
      seiRubyList.push(seiruby.substring(0, foundIndex));
      seiruby = seiruby.substring(foundIndex+1);
      foundIndex = seiruby.indexOf(searchWord);
    }
    seiRubyList.push(seiruby);
    //姓ルビ(モノルビ)間の距離を算出
    def a = pSei.param.size;
    def b = rubySize;
    def c = jidori[jidoriId][3];
    def n = seiRubyList.size();
    def seiRubySpan = [];
    seiRubySpan[0] = (a - (b * seiRubyList[0].size()))/2;
    for (i=1; i<n; i++){
      seiRubySpan[i] = c + (2 * a - (b * (seiRubyList[i-1].size() + seiRubyList[i].size())))/2;
    }
    //姓ルビ(モノルビ)テキストの生成と配置
    def seiRubyText = '';
    for (i=0; i<n ; i++){
      seiRubyText += '<font size="' + seiRubySpan[i] + 'pt">　</font>' + seiRubyList[i];
    }
    pSeiRuby.param.text = '<p>' + seiRubyText + '</p>';
    pSeiRuby.transform.translateX = pSei.transform.translateX;
    pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height + (rubySize*0.35) - rubySpan;
    pSeiRuby.param.text = "ルビのテスト";
  }
  r = pSeiRuby;
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  def additionalLabelList = ['結合住所','TEL1結合','結合住所2','TEL2結合','TEL1FAX1英字結合'];

  //変数取得
  def title1 =record['肩書き1'];
  def title2 =record['肩書き2'];
  def title3 =record['肩書き3'];
  def sei = record['姓'];
  def mei = record['名'];
  def seiruby = record['姓ルビ'];
  def meiruby = record['名ルビ'];
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
  def title1Eng =record['所属1英字'];
  def title2Eng =record['所属2英字'];
  def title3Eng =record['所属3英字'];
  def title4Eng =record['所属4英字'];
  def adr1Eng = record['住所1英字1行目'];
  def adr12Eng = record['住所1英字2行目'];
  def fsc = record['FSCロゴ'];
  def iso = record['ISOロゴ'];
  def dmr = record['DMRロゴ'];
  def mra = record['MRAロゴ'];
  def addressList = [adr1,adr12,tel1,mobile,email,adrName2,adr2,tel2,url];

  //全角英数字校正
  postnum1 = fullwidthCorrector(postnum1);
  postnum2 = fullwidthCorrector(postnum2);
  email = fullwidthCorrector(email);
  tel1 = telnumBuilder(replacer(fullwidthCorrector(tel1)));
  fax1 = telnumBuilder(replacer(fullwidthCorrector(fax1)));
  tel2 = telnumBuilder(replacer(fullwidthCorrector(tel2)));
  fax2 = telnumBuilder(replacer(fullwidthCorrector(fax2)));
  mobile = replacer(fullwidthCorrector(mobile));

  //表面住所データ結合
  def address1 = '〒' + postnum1 + ' ' + adr1;
  record['結合住所'] = address1;

  //表面電話番号結合
  def telfax1= 'TEL ' + tel1 + '/FAX ' + fax1 ;
  record['TEL1結合'] = telfax1;

  //表面住所データ結合2
  def address2 = '〒' + postnum2 + ' ' + adr2;
  record['結合住所2'] = address2;

  //表面電話番号結合2
  def telfax2= 'TEL ' + tel2 + '/FAX ' + fax2 ;
  record['TEL2結合'] = telfax2;

  //裏面電話番号結合
  def telfax1Eng= 'TEL +81 ' + tel1.substring(1) + '/FAX +81 ' + fax1.substring(1) ;
  record['TEL1FAX1英字結合'] = telfax1Eng;

  //基本関数
  labelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }

  //追加ラベルへの差し込み
  additionalLabelList.each {
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
    def pSeiRuby = getPartsByLabel('姓ルビ',1,cassette);
    def pMeiRuby = getPartsByLabel('名ルビ',1,cassette);
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
    def pFsc = getPartsByLabel('FSCロゴ画像',1,cassette);
    def pIso = getPartsByLabel('ISOロゴ画像',1,cassette);
    def pDmr = getPartsByLabel('DMRロゴ画像',1,cassette);
    def pMra = getPartsByLabel('MRAロゴ画像',1,cassette);

    //非結合項目の差込
    pMobile1.param.text = '携帯: ' + mobile;
    pEmail1.param.text = 'Email: ' + email;

    //住所欄の行数計算
    sumlines = 0;
    sumlines = calclines(sumlines,addressList);

    //氏名のY位置変更
    positionY = 34;
    if(sumlines >= 5 && sumlines < 7){
      positionY = 32;
    } else if(sumlines >= 7){
      positionY = 29;
    }
    pSei.transform.translateY = positionY;
    pMei.transform.translateY = positionY;

    //字取り
    //デフォルト設定
    positionX = 28;
    def span = 5;//全角スペース1個分(mm)
    //Jidori＝[姓文字数、名文字数、姓名間全角スペース比、姓スペース(pt)、名スペース(pt)]
    def jidori = [
      [1,1,2,0,0],
      [1,2,1.5,7,7],
      [1,3,1.5,7,7],
      [2,1,1.5,7,7],
      [2,2,1,7,7],
      [2,3,1,7,7],
      [3,1,1.5,7,7],
      [3,2,1,7,7],
      [3,3,1,3.5,3.5],
      [0,0,0.5,0,0]
      ];
    jidoriBuilder(jidori,sei,mei,pSei,pMei,span,positionX);

    //ルビの設定
    def rubySize = 5;//ルビの文字サイズ指定(pt)
    def rubyFont = 'FOT-ロダン Pro M';//ルビのフォント
    def rubySpan = 0.5;//ルビと氏名の距離
    searchWord = '/';
    pSeiRuby.param.size = pMeiRuby.param.size = rubySize;
    pSeiRuby.param.font = pMeiRuby.param.font = rubyFont;

    //姓ルビの条件分岐（モノルビorセンタールビ）
    foundIndex = seiruby.indexOf(searchWord);
    if (foundIndex < 0){
      //姓ルビ(センター)配置
      if(seiruby){
        pSeiRuby.editReferencePoint('center-center',keepReferencePointPosition = false);
        pSeiRuby.transform.translateX = pSei.transform.translateX + pSei.boundBox.width / 2;
        pSeiRuby.param.maxWidth = pSei.boundBox.width;
        pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height - rubySpan;
      }
    } else {
      //姓ルビ(モノルビ)配置
      //姓ルビ(モノルビ)を区切り文字”/”で分解、配列に追加
      def seiRubyList = [];
      while (foundIndex >= 0){
        seiRubyList.push(seiruby.substring(0, foundIndex));
        seiruby = seiruby.substring(foundIndex+1);
        foundIndex = seiruby.indexOf(searchWord);
      }
      seiRubyList.push(seiruby);
      //姓ルビ(モノルビ)間の距離を算出
      def a = pSei.param.size;
      def b = rubySize;
      def c = jidori[jidoriId][3];
      def n = seiRubyList.size();
      def seiRubySpan = [];
      seiRubySpan[0] = (a - (b * seiRubyList[0].size()))/2;
      for (i=1; i<n; i++){
        seiRubySpan[i] = c + (2 * a - (b * (seiRubyList[i-1].size() + seiRubyList[i].size())))/2;
      }
      //姓ルビ(モノルビ)テキストの生成と配置
      def seiRubyText = '';
      for (i=0; i<n ; i++){
        seiRubyText += '<font size="' + seiRubySpan[i] + 'pt">　</font>' + seiRubyList[i];
      }
      pSeiRuby.param.text = '<p>' + seiRubyText + '</p>';
      pSeiRuby.transform.translateX = pSei.transform.translateX;
      pSeiRuby.transform.translateY = pSei.transform.translateY - pSei.boundBox.height + (rubySize*0.35) - rubySpan;
    }

    //姓ルビの条件分岐（モノルビorセンタールビ）
    foundIndex = meiruby.indexOf(searchWord);
    if (foundIndex < 0){
      //名ルビ(センター)配置
      if(meiruby){
        pMeiRuby.editReferencePoint('center-center',keepReferencePointPosition = true);
        pMeiRuby.transform.translateX = pMei.transform.translateX + pMei.boundBox.width / 2;
        pMeiRuby.param.maxWidth = pMei.boundBox.width;
        pMeiRuby.transform.translateY = pMei.transform.translateY - pSei.boundBox.height - rubySpan;
      }
    } else {
      //名ルビ(モノルビ)配置
      //名ルビ(モノルビ)を区切り文字”/”で分解、配列に追加
      def meiRubyList = [];
      while (foundIndex >= 0){
        meiRubyList.push(meiruby.substring(0, foundIndex));
        meiruby = meiruby.substring(foundIndex+1);
        foundIndex = meiruby.indexOf(searchWord);
      }
      meiRubyList.push(meiruby);
      //名ルビ(モノルビ)間の距離を算出
      def a = pMei.param.size;
      def b = rubySize;
      def c = jidori[jidoriId][3];
      def n = meiRubyList.size();
      def meiRubySpan = [];
      meiRubySpan[0] = (a - (b * meiRubyList[0].size()))/2;
      for (i=1; i<n; i++){
        meiRubySpan[i] = c + (2 * a - (b * (meiRubyList[i-1].size() + meiRubyList[i].size())))/2;
      }
      //名ルビ(モノルビ)テキストの生成と配置
      def meiRubyText = '';
      for (i=0; i<n ; i++){
        meiRubyText += '<font size="' + meiRubySpan[i] + 'pt">　</font>' + meiRubyList[i];
      }
      pMeiRuby.param.text = '<p>' + meiRubyText + '</p>';
      pMeiRuby.transform.translateX = pMei.transform.translateX;
      pMeiRuby.transform.translateY = pMei.transform.translateY - pSei.boundBox.height + (rubySize*0.35) - rubySpan;
    }

    //テスト
    def test = getPartsByLabel('テスト',1,cassette);
    testruby = 'テスト/で/すよ';
    test = function(test,rubySpan,pSei,seiruby,searchWord,rubySize,jidori,jidoriId);
    //test.setDisplay("none");


    //肩書きが空の場合段落を詰める
    def titleList = [title1,title2,title3];
    def pTitleList = [pTitle1,pTitle2,pTitle3];
    linespan = 0;
    lineheight = 2.5;
    positionY = pMei.boundBox.y - 2;
    if(sumlines > 7){pMei.boundBox.y - 1.5;}
    paragraphBuilder(titleList,pTitleList,positionY,linespan,lineheight);

    //住所行の文字のサイズ変更
    lineheight = 2.8;
    if(sumlines >= 5 && sumlines < 7){
      lineheight = 2.5;
      for(i=0;i>8;i++){
        pAddressList[i].param.size = 6.38;
      }
    } else if(sumlines >= 7){
      lineheight = 2.3;
      for(i=0;i>8;i++){
        pAddressList[i].param.size = 5.67;
      }
    }

    //住所行が空の場合段落を詰める
    linespan = 0;
    //lineheight = 住所行の文字のサイズ変更で定義;
    positionY = 51.5;
    paragraphBuilder(addressList,pAddressList,positionY,linespan,lineheight);

    //FSCの表示
    if (fsc != 'あり'){
      pFsc.setDisplay("none");
    }
    //ISOの表示
    if (iso != 'あり'){
      pIso.setDisplay("none");
      pFsc.transform.translateY += 11;
    }
    //DMRの表示
    if (dmr != 'あり'){
      pDmr.setDisplay("none");
      pFsc.transform.translateY += 8;
      pIso.transform.translateY += 8;
    }
    //MRAの表示
    if (mra == 'あり'){
      pFsc.setDisplay("none");
      pIso.setDisplay("none");
      pDmr.setDisplay("none");
    }else{
      pMra.setDisplay("none");
    }

  }else{
    //裏面のパーツ操作スクリプト
    //関連パーツ取得
    def pTitle1Eng = getPartsByLabel('所属1英字',1,cassette);
    def pTitle2Eng = getPartsByLabel('所属2英字',1,cassette);
    def pTitle3Eng = getPartsByLabel('所属3英字',1,cassette);
    def pTitle4Eng = getPartsByLabel('所属4英字',1,cassette);
    def pAdr1Eng = getPartsByLabel('住所1英字1行目',1,cassette);
    def pAdr12Eng = getPartsByLabel('住所1英字2行目',1,cassette);
    def pTelFax1Eng = getPartsByLabel('TEL1FAX1英字結合',1,cassette);
    def pEmail1 = getPartsByLabel('Email',1,cassette);
    def pURL = getPartsByLabel('URL',1,cassette);

    //肩書きが空の場合段落を詰める
    def titleEngList = [title1Eng,title2Eng,title3Eng,title4Eng];
    def pTitleEngList = [pTitle1Eng,pTitle2Eng,pTitle3Eng,pTitle4Eng];
    linespan = 0;
    lineheight = 2.5;
    sumlines = 0;
    sumlines = calclines(sumlines,titleEngList);
    positionY = 28;
    if(sumlines < 2){
      positionY = 21;
    } else if(sumlines < 3){
      positionY = 24;
    } else if(sumlines < 4){
      positionY = 26;
    }
    paragraphBuilder(titleEngList,pTitleEngList,positionY,linespan,lineheight);

    //住所行が空の場合段落を詰める
    def addressEngList = [adr1Eng,adr12Eng,tel1,email,url];
    def pAddressEngList = [pAdr1Eng,pAdr12Eng,pTelFax1Eng,pEmail1,pURL];
    linespan = 0;
    lineheight = 2.5;
    positionY = 51.5;
    paragraphBuilder(addressEngList,pAddressEngList,positionY,linespan,lineheight);

    //社名英字の配置
    def pCorpnameEng = getPartsByLabel('社名英字',1,cassette);
    pCorpnameEng.transform.translateY = pAdr1Eng.transform.translateY - pAdr1Eng.boundBox.height;

  }

}
