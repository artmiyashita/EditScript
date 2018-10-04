/*
 * 名刺用 EditScript サンプル
 */

@import injectionValiable

env.injectionOne = {cassette, record, labelList, imageTable ->
    myInjectionOne(cassette, record, labelList, imageTable);
};

if(!env.multiLayout) {
    doProduce(2, env.imageTable); // ページ数 2
}

// ----------

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

	//追加ラベルリストがある場合にはここに記述
	//	ex. def additionalLabelList = ["住所全体","氏名"];
	def additionalLabelList = [];

	//レコードデータなどを使って、新たなラベルを追加する場合ここに記述
	//	ex. record['住所全体'] = record['住所1'] + " " + record['住所2'];
	//	    record['氏名'] = record['氏'] + " " + record['名'];

	//差し込み処理
    labelList.each {
        injectionOneParts(cassette, it , record, imageTable);
    }

	//追加ラベル差し込み処理
	additionalLabelList.each {
        injectionOneParts(cassette, it , record, imageTable);
	}

	//差し込み後処理がある場合はここに記述

  def Mobile = getPartsByLabel('Mobile',1,cassette);
  def Mobilen = getPartsByLabel('Mobilen',1,cassette);
  def Mobiler = record['Mobile'];

  def Alphabet = getPartsByLabel('アルファベット',1,cassette);

  def Back = getPartsByLabel('台紙裏',1,cassette);

if (Mobile && Alphabet && Mobilen){
//Mobile処理
    Mobile.param.text = Mobiler;

    if (Mobiler == ""){
    Mobilen.setDisplay("none");
  }
//@アルファベット処理
switch (record['アルファベット']){
    case "meishilogo_A.ai":
    Alphabet.param.trackingID = "eef038e5c0a80029218fe96c359b09b7";
    break;

    case "meishilogo_C.ai":
    Alphabet.param.trackingID = "eeefc4b0c0a800297cec6930a0f5a6d5";
    break;

    case "meishilogo_D.ai":
    Alphabet.param.trackingID = "eeefcd28c0a800296cf36efd43bb020a";
    break;

    case "meishilogo_E.ai":
    Alphabet.param.trackingID = "eeefd37fc0a800296d83d8bdd6eae55d";
    break;

    case "meishilogo_F.ai":
    Alphabet.param.trackingID = "eeefd9e5c0a800293b2f9764315dbf70";
    break;

    case "meishilogo_G.ai":
    Alphabet.param.trackingID = "eeefe02bc0a800290b26fc6c0f2d7412";
    break;

    case "meishilogo_H.ai":
    Alphabet.param.trackingID = "eeefe672c0a800296f40fdc0b374fb05";
    break;

    case "meishilogo_I.ai":
    Alphabet.param.trackingID = "eeefecb9c0a8002908443831121272bf";
    break;

    case "meishilogo_K.ai":
    Alphabet.param.trackingID = "eeeff300c0a8002950ad2f83a77a0337";
    break;

    case "meishilogo_M.ai":
    Alphabet.param.trackingID = "eeeff947c0a8002902f4bc241a48cbe9";
    break;

    case "meishilogo_miuraY.ai":
    Alphabet.param.trackingID = "eeefff9dc0a80029459da66bca505633";
    break;

    case "meishilogo_N.ai":
    Alphabet.param.trackingID = "eef00603c0a8002966752870edc4cf02";
    break;

    case "meishilogo_O.ai":
    Alphabet.param.trackingID = "eef00c5ac0a80029515273795f9c86ab";
    break;

    case "meishilogo_S.ai":
    Alphabet.param.trackingID = "eef012c0c0a800297d8dc94e1bee47bc";
    break;

    case "meishilogo_T.ai":
    Alphabet.param.trackingID = "eef01916c0a800297f8d29bd39dc91da";
    break;

    case "meishilogo_U.ai":
    Alphabet.param.trackingID = "eef01f6cc0a800296d0cf2d9b153e4d5";
    break;

    case "meishilogo_W.ai":
    Alphabet.param.trackingID = "eef025c3c0a8002941d94e08cd28206f";
    break;

    case "meishilogo_Y.ai":
    Alphabet.param.trackingID = "eef02c19c0a800296e8251fe5ba9917f";
    break;

    case "meishilogo_Yoshizumi.ai":
    Alphabet.param.trackingID = "eef0329fc0a800294349b4da691e6041";
    break;
  }
}

if (Back){

  switch(record['台紙裏']){
    case "Back_eigyo.ai":
    Back.param.trackingID = "ef02fb04c0a80029089f18cbaddd4b95";
    break;

    case "Back_honsya.ai":
    Back.param.trackingID = "ef0355b1c0a8002908932ede28f0d064";
    break;

    case "Back_logi.ai":
    Back.param.trackingID = "ef034f2bc0a800296092a6aa75634263";
    break;

    case "Back_kojyo.ai":
    Back.param.trackingID = "ef034897c0a80029095aa87ce816c154";
    break;
    }
  }
}
